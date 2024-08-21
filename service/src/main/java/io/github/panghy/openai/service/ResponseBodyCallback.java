package io.github.panghy.openai.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.panghy.openai.OpenAiError;
import io.github.panghy.openai.OpenAiHttpException;
import io.reactivex.FlowableEmitter;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Callback to parse Server Sent Events (SSE) from raw InputStream and
 * emit the events with io.reactivex.FlowableEmitter to allow streaming of
 * SSE.
 */
public class ResponseBodyCallback implements Callback<ResponseBody> {
  private static final ObjectMapper mapper = OpenAiService.defaultObjectMapper();

  private final FlowableEmitter<SSE> emitter;
  private final boolean emitDone;

  public ResponseBodyCallback(FlowableEmitter<SSE> emitter, boolean emitDone) {
    this.emitter = emitter;
    this.emitDone = emitDone;
  }

  @Override
  public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
    BufferedReader reader = null;

    try {
      if (!response.isSuccessful()) {
        HttpException e = new HttpException(response);
        try (ResponseBody errorBody = response.errorBody()) {

          if (errorBody == null) {
            throw e;
          } else {
            OpenAiError error = mapper.readValue(
                errorBody.string(),
                OpenAiError.class
            );
            throw new OpenAiHttpException(error, e, e.code());
          }
        }
      }

      try (ResponseBody body = response.body()) {
        if (body == null) {
          throw new IOException("Response body is null!");
        }
        try (InputStream in = body.byteStream()) {
          reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
          String line;
          SSE sse = null;

          while (!emitter.isCancelled() && (line = reader.readLine()) != null) {
            if (line.startsWith("data:")) {
              String data = line.substring(5).trim();
              if (sse == null) {
                sse = new SSE();
              }
              sse.setData(data);
            } else if (line.startsWith("event:")) {
              String eventName = line.substring(6).trim();
              if (sse == null) {
                sse = new SSE();
              }
              sse.setEvent(eventName);
            } else if (line.isEmpty() && sse != null) {
              if (sse.isDone()) {
                if (emitDone) {
                  emitter.onNext(sse);
                }
                break;
              }

              emitter.onNext(sse);
              sse = null;
            } else {
              throw new SSEFormatException("Invalid sse format! " + line);
            }
          }
        }
      }

      emitter.onComplete();

    } catch (Throwable t) {
      onFailure(call, t);
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          // do nothing
        }
      }
    }
  }

  @Override
  public void onFailure(Call<ResponseBody> call, Throwable t) {
    emitter.onError(t);
  }
}
