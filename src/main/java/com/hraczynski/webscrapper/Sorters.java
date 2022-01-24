package com.hraczynski.webscrapper;

import com.hraczynski.webscrapper.dto.SingleOffer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Comparator;

@RequiredArgsConstructor
@Getter
public enum Sorters {
    BY_TITLE("title", (o1, o2) -> o2.getTitle().compareToIgnoreCase(o1.getTitle())),
    BY_PRICE("price", (o1, o2) -> compareDouble(o2.getPrice(), o1.getPrice())),
    BY_AREA("area", (o1, o2) -> compareDouble(o2.getArea(), o1.getArea())),
    BY_ROOMS("rooms", (o1, o2) -> compareDouble(o2.getRooms(), o1.getRooms(), true));

    private final String order;
    private final Comparator<SingleOffer> singleOfferComparator;

    public static Comparator<SingleOffer> findOrderByCode(String order) {
        return Arrays.stream(values())
                .filter(s -> s.order.equals(order))
                .map(s -> s.singleOfferComparator)
                .findFirst()
                .orElseThrow(() -> new UnrecognizedCodeException(order));
    }

    private static int compareDouble(String o1, String o2) {
        return compareDouble(o1, o2, false);
    }

    private static int compareDouble(String o1, String o2, boolean defaultZero) {
        return Double.compare(NumberUtils.parseNumber(o1, defaultZero), NumberUtils.parseNumber(o2,defaultZero));
    }
}
