package com.hraczynski.webscrapper;

import com.hraczynski.webscrapper.dto.SingleOffer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.function.Function;

@RequiredArgsConstructor
@Getter
public enum Filters {
    BY_TITLE("title", SingleOffer::getTitle),
    BY_PRICE("price", SingleOffer::getPrice),
    BY_AREA("area", SingleOffer::getArea),
    BY_ROOMS("rooms", SingleOffer::getRooms);

    private final String filter;
    private final Function<SingleOffer, String> mapper;

    public static Function<SingleOffer, String> getMapperByFilter(String filter) {
        return Arrays.stream(values())
                .filter(s -> s.filter.equalsIgnoreCase(filter))
                .map(s -> s.mapper)
                .findFirst()
                .orElseThrow(() -> new UnrecognizedCodeException(filter));
    }
}
