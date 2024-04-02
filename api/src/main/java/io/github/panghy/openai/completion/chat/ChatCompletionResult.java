package io.github.panghy.openai.completion.chat;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.github.panghy.openai.Usage;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

/**
 * Object containing a response from the chat completions api.
 */
@Data
@Jacksonized
@Builder
public class ChatCompletionResult {

    /**
     * Unique id assigned to this chat completion.
     */
    String id;

    /**
     * The type of object returned, should be "chat.completion"
     */
    String object;

    /**
     * The creation time in epoch seconds.
     */
    long created;
    
    /**
     * The GPT model used.
     */
    String model;

    /**
     * A list of all generated completions.
     */
    List<ChatCompletionChoice> choices;

    /**
     * The API usage for this request.
     */
    Usage usage;

    /**
     * This fingerprint represents the backend configuration that the model runs with. Can be used in conjunction with
     * the seed request parameter to understand when backend changes have been made that might impact determinism.
     */
    @JsonAlias("system_fingerprint")
    String systemFingerprint;
}
