package example;

import io.github.panghy.openai.audio.CreateTranscriptionRequest;
import io.github.panghy.openai.audio.TranscriptionResult;
import io.github.panghy.openai.service.OpenAiService;

import java.net.URL;
import java.time.Duration;

public class OpenAiApiTranscriptionExample {
  public static void main(String[] args) {
    String token = System.getenv("OPENAI_TOKEN");
    OpenAiService service = new OpenAiService(token, Duration.ofSeconds(30));

    URL resource = OpenAiService.class.getResource("/hello-world.mp3");
    TranscriptionResult transcription = service.createTranscription(CreateTranscriptionRequest.builder().
        model("whisper-1").
        build(), resource.getPath());
    System.out.println(transcription);
  }
}
