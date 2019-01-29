package io.thingcare.webfluxstreams.event.api;

import io.thingcare.webfluxstreams.api.Event;
import io.thingcare.webfluxstreams.api.SearchCriteria;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public interface EventRepository {
    Stream<SearchCriteria> evaluateCriteria(UUID uuid);

    List<Event> events(SearchCriteria criteria);
}
