package io.github.panghy.openai.image;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

/**
 * An object containing either a URL or a base 64 encoded image.
 *
 * https://beta.openai.com/docs/api-reference/images
 */
@Data
@Jacksonized
@Builder
public class Image {
    /**
     * The URL where the image can be accessed.
     */
    String url;


    /**
     * Base64 encoded image string.
     */
    @JsonProperty("b64_json")
    String b64Json;

    /**
     * The prompt that was used to generate the image, if there was any revision to the prompt.
     */
    @JsonProperty("revised_prompt")
    String revisedPrompt;
}
