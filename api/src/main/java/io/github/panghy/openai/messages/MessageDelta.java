package io.github.panghy.openai.messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Represents a delta of a Message within a thread.
 * <p>
 * <a href="https://platform.openai.com/docs/api-reference/messages/object">...</a>
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MessageDelta {
    /**
     * The identifier, which can be referenced in API endpoints.
     */
    String id;

    /**
     * The object type, which is always thread.message.
     */
    String object;

    /**
     * The message delta received.
     */
    MessageContentDelta delta;
}