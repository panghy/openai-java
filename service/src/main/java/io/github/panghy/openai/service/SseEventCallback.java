package io.github.panghy.openai.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.panghy.openai.messages.Message;
import io.github.panghy.openai.messages.MessageDelta;
import io.github.panghy.openai.runs.Run;
import io.github.panghy.openai.runs.RunStepDelta;
import io.github.panghy.openai.runs.StepDetails;
import io.github.panghy.openai.threads.Thread;
import lombok.Data;

import java.util.function.Function;

/**
 * Given an event name of an SSE block, parse the JSON accordingly and generate a wrapped response.
 */
public class SseEventCallback implements Function<SSE, SseEventCallback.Event> {

  private static final ObjectMapper mapper = OpenAiService.defaultObjectMapper();

  @Override
  public Event apply(SSE sse) {
    if (sse.getEvent() == null) {
      return null;
    }

    Event toReturn = new Event();
    toReturn.setEventName(sse.getEvent());
    try {
      if (sse.getEvent().startsWith("thread.message.delta")) {
        toReturn.delta = mapper.readValue(sse.getData(), MessageDelta.class);
      } else if (sse.getEvent().startsWith("thread.message")) {
        toReturn.message = mapper.readValue(sse.getData(), Message.class);
      } else if (sse.getEvent().startsWith("thread.run.step.delta")) {
        toReturn.runStepDelta = mapper.readValue(sse.getData(), RunStepDelta.class);
      } else if (sse.getEvent().startsWith("thread.run.step")) {
        toReturn.stepDetails = mapper.readValue(sse.getData(), StepDetails.class);
      } else if (sse.getEvent().startsWith("thread.run.requires_action")) {
        toReturn.run = mapper.readValue(sse.getData(), Run.class);
      } else if (sse.getEvent().startsWith("thread.run")) {
        toReturn.run = mapper.readValue(sse.getData(), Run.class);
      } else if (sse.getEvent().startsWith("thread")) {
        toReturn.thread = mapper.readValue(sse.getData(), Thread.class);
      } else {
        throw new RuntimeException("Unknown event: " + sse.getEvent());
      }
    } catch (
        Exception e) {
      throw new RuntimeException(e);
    }
    return toReturn;
  }

  @Data
  public static class Event {

    String eventName;

    Thread thread;
    Run run;
    RunStepDelta runStepDelta;
    StepDetails stepDetails;
    Message message;
    MessageDelta delta;
  }
}
