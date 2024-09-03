package io.github.panghy.openai.runs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmitToolOutputsRequest {
    
    @JsonProperty("tool_outputs")
    List<SubmitToolOutputRequestItem> toolOutputs;

    boolean stream;
}
