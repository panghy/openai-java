package io.github.panghy.openai.fine_tuning;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Hyperparameters for a fine-tuning job
 * https://platform.openai.com/docs/api-reference/fine-tuning/object#hyperparameters
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Hyperparameters {

    /**
     * The number of epochs to train the model for. An epoch refers to one full cycle
     * through the training dataset.
     */
    @JsonProperty("n_epochs")
    String nEpochs;

    /**
     * Number of examples in each batch. A larger batch size means that model parameters
     * are updated less frequently, but with lower variance.
     */
    @JsonProperty("batch_size")
    String batchSize;

    /**
     * Scaling factor for the learning rate. A smaller learning rate may be useful to
     * avoid overfitting.
     */
    @JsonProperty("learning_rate_multiplier")
    String learningRateMultiplier;
}
