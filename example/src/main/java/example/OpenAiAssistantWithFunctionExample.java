package example;

import io.github.panghy.openai.ListSearchParameters;
import io.github.panghy.openai.OpenAiResponse;
import io.github.panghy.openai.assistants.Assistant;
import io.github.panghy.openai.messages.MessageRequest;
import io.github.panghy.openai.runs.*;
import io.github.panghy.openai.service.OpenAiService;
import io.github.panghy.openai.service.SseEventCallback;
import io.github.panghy.openai.threads.ThreadRequest;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpenAiAssistantWithFunctionExample {

  public static void main(String[] args) {
    String token = System.getenv("OPENAI_TOKEN");
    OpenAiService service = new OpenAiService(token, Duration.ofSeconds(30));

    OpenAiResponse<Assistant> assistantOpenAiResponse =
        service.listAssistants(ListSearchParameters.builder().build());
    System.out.println(assistantOpenAiResponse);

    String assistantId = assistantOpenAiResponse.firstId;

    SseEventCallback.Event threadEvent = service.streamCreateThreadAndRun(CreateThreadAndRunRequest.builder().
        assistantId(assistantId).
        thread(ThreadRequest.builder().
            messages(List.of(
                MessageRequest.builder().
                    content("Hello, how are you?").
                    build()
            )).
            build()).
        build()).doOnEach(System.out::println).filter(event -> event.getThread() != null).blockingLast();

    String threadId = threadEvent.getThread().getId();
    System.out.println("Thread ID: " + threadId);

    Map<Run, List<ToolCall>> toolCalls = new HashMap<>();
    service.streamCreateRun(threadId, RunCreateRequest.builder().
            assistantId(assistantId).
            additionalMessages(List.of(
                MessageRequest.builder().
                    content("I would you to start the session right now!").
                    build()
            )).
            build()).
        doOnEach(event -> {
          if (event.getValue() != null && event.getValue().getEventName().equals("thread.run.requires_action")) {
            toolCalls.computeIfAbsent(event.getValue().getRun(), run -> new ArrayList<>()).
                addAll(event.getValue().getRun().getRequiredAction().getSubmitToolOutputs().getToolCalls());
          }
        }).blockingLast();

    System.out.println("Tool Calls: " + toolCalls);
    for (Map.Entry<Run, List<ToolCall>> entry : toolCalls.entrySet()) {
      Run run = entry.getKey();
      List<ToolCall> toolCallList = entry.getValue();
      service.submitToolOutputsStream(run.getThreadId(), run.getId(), SubmitToolOutputsRequest.builder().
          toolOutputs(toolCallList.stream().map(toolCall -> SubmitToolOutputRequestItem.builder().
              toolCallId(toolCall.getId()).
              output("{success: \"true\"}").
              build()).toList()).
          build()).doOnEach(System.out::println).blockingLast();
    }
  }
}
