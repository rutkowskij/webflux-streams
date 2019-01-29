package io.thingcare.webfluxstreams.client;

import io.thingcare.webfluxstreams.api.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import static java.lang.String.format;

@Slf4j
@RestController
@RequestMapping(path = "")
@RequiredArgsConstructor
public class EventClient {

    @GetMapping(value = "/test")
    public Flux<String> testEvents() {

        final AtomicLong counter = new AtomicLong();
        String url = format("http://localhost:8080/api/events/%s", UUID.randomUUID());

        WebClient client = WebClient
                .builder()
                .baseUrl(url)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_STREAM_JSON_VALUE)
                .build();

        return client.get()
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .flatMapMany(cr -> cr.bodyToFlux(Event.class))
                .map(Event::toString)
                .log()
                .doOnNext(t -> log.info("counter: {}", counter.incrementAndGet()))
                .doOnComplete(() -> log.info("complete"))
                .doFinally(signalType -> log.info("request finished! - {} ", signalType));
    }
}
