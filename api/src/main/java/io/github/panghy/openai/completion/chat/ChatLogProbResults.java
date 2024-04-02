package io.github.panghy.openai.completion.chat;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Builder
@Jacksonized
public class ChatLogProbResults {

  /**
   * A list of message content tokens with log probability information.
   */
  List<ChatLogProb> content;
}
