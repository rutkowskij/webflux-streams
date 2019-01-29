package io.thingcare.webfluxstreams.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
@ToString(includeFieldNames = false)
public class SearchCriteria {
    private final UUID uuid;
    private final int part;

}
