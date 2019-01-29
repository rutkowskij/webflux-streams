package io.thingcare.webfluxstreams.generator;

import io.thingcare.webfluxstreams.api.Event;
import io.thingcare.webfluxstreams.api.SearchCriteria;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class EventGenerator {

    public static List<Event> events(SearchCriteria criteria) {
        try {
            int quantity = RandomUtils.nextInt(11000, 30000);
            Thread.sleep(quantity);
            return IntStream.rangeClosed(0, quantity - 1)
                    .mapToObj(i -> new Event(UUID.randomUUID()))
                    .collect(Collectors.toList());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static Stream<SearchCriteria> searchCriteria(UUID uuid) {
        int quantity = RandomUtils.nextInt(5, 15);
        return IntStream.rangeClosed(0, quantity - 1)
                .mapToObj(i -> new SearchCriteria(uuid, i));

    }
}
