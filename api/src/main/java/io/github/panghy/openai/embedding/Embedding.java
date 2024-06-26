package io.github.panghy.openai.embedding;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

/**
 * Represents an embedding returned by the embedding api
 *
 * https://beta.openai.com/docs/api-reference/classifications/create
 */
@Data
@Jacksonized
@Builder
public class Embedding {

    /**
     * The type of object returned, should be "embedding"
     */
    String object;

    /**
     * The embedding vector
     */
    List<Double> embedding;

    /**
     * The position of this embedding in the list
     */
    Integer index;
}
