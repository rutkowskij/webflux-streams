package io.thingcare.webfluxstreams.event.api;

import io.thingcare.webfluxstreams.api.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@RestController
@RequestMapping(path = "/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService service;

    @GetMapping(value = "/{uuid}", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Event> events(@PathVariable UUID uuid) {
        final AtomicLong counter = new AtomicLong();

        return service.events(uuid)
                .doOnNext(t -> log.info("counter: {}", counter.incrementAndGet()));
    }
}
