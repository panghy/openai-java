package io.github.panghy.openai.runs;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.panghy.openai.common.LastError;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RunStepDelta {

  private String id;

  private String object;

  private StepDetailsDelta delta;
}
