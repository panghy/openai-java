package io.github.panghy.openai.service;

import lombok.Data;

/**
 * Simple Server Sent Event representation
 */
@Data
public class SSE {
  private static final String DONE_DATA = "[DONE]";

  String data;
  String event;

  public byte[] toBytes() {
    return String.format("data: %s\n\n", this.data).getBytes();
  }

  public boolean isDone() {
    return DONE_DATA.equalsIgnoreCase(this.data);
  }
}