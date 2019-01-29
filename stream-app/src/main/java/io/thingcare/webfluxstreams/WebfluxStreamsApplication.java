package io.thingcare.webfluxstreams;

import io.thingcare.webfluxstreams.api.Event;
import io.thingcare.webfluxstreams.api.SearchCriteria;
import io.thingcare.webfluxstreams.event.api.EventRepository;
import io.thingcare.webfluxstreams.generator.EventGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class WebfluxStreamsApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebfluxStreamsApplication.class, args);
    }

    @Bean
    EventRepository repository() {
        return new EventRepository() {
            @Override
            public Stream<SearchCriteria> evaluateCriteria(UUID uuid) {
                return EventGenerator.searchCriteria(uuid);
            }

            @Override
            public List<Event> events(SearchCriteria criteria) {
                return EventGenerator.events(criteria);
            }
        };
    }
}

