package example;

import io.github.panghy.openai.ListSearchParameters;
import io.github.panghy.openai.OpenAiResponse;
import io.github.panghy.openai.assistants.Assistant;
import io.github.panghy.openai.messages.MessageRequest;
import io.github.panghy.openai.runs.CreateThreadAndRunRequest;
import io.github.panghy.openai.runs.RunCreateRequest;
import io.github.panghy.openai.service.OpenAiService;
import io.github.panghy.openai.service.SseEventCallback;
import io.github.panghy.openai.threads.ThreadRequest;

import java.time.Duration;
import java.util.List;

public class OpenAiAssistantExample {

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
    service.streamCreateRun(threadId, RunCreateRequest.builder().
        assistantId(assistantId).
        additionalMessages(List.of(
            MessageRequest.builder().
                content("I'm doing well, thank you! What is you name?").
                build()
        )).
        build()).doOnEach(System.out::println).blockingLast();
  }
}
