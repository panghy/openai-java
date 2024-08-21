package io.github.panghy.openai.messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * Represents a content delta of a Message within a thread.
 * <p>
 * <a href="https://platform.openai.com/docs/api-reference/messages/object">...</a>
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MessageContentDelta {

    List<MessageContent> content;
}