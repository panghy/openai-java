package io.github.panghy.openai.runs;

import io.github.panghy.openai.assistants.Tool;
import io.github.panghy.openai.messages.MessageRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RunCreateRequest {
  /**
   * Required.
   */
  String assistantId;
  /**
   * Optional.
   */
  String model;
  /**
   * Optional.
   */
  String instructions;
  /**
   * Optional.
   * <p></p>
   * Appends additional instructions at the end of the instructions for the run. This is useful for modifying the
   * behavior on a per-run basis without overriding other instructions.
   */
  String additionalInstructions;

  /**
   * Optional.
   * <p></p>
   * Adds additional messages to the thread before creating the run.
   */
  List<MessageRequest> additionalMessages;

  List<Tool> tools;

  Map<String, String> metadata;

  Boolean stream;
}
