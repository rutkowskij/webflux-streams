package io.thingcare.webfluxstreams.event.api;

import io.thingcare.webfluxstreams.api.Event;
import io.thingcare.webfluxstreams.api.SearchCriteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository repository;

    Flux<Event> events(UUID uuid) {
        List<SearchCriteria> criteriaList = repository.evaluateCriteria(uuid).collect(Collectors.toList());
        AtomicInteger counter = new AtomicInteger();
        return Flux.push(sink -> {
            criteriaList.stream()
                    .peek(criteria -> log.info("request : {}/{}", counter.incrementAndGet(), criteriaList.size()))
                    .flatMap(criteria -> repository.events(criteria).stream())
                    .forEach(sink::next);
            sink.complete();
        });
    }
}
