package com.hraczynski.webscrapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Getter
public enum ValueRanges {
    PRICE("price", List.of(new Range(0, 150000), new Range(150001, 550000), new Range(550001, 1500000), new Range(1500001, Double.MAX_VALUE))),
    PRICE_MC("price_mc", List.of(new Range(0, 800), new Range(801, 1500), new Range(1501, 3000), new Range(3001, Double.MAX_VALUE))),
    ROOMS("rooms", List.of(new Range(0, 0), new Range(1,1), new Range(2, 2), new Range(3, 3), new Range(4, 4), new Range(5, 5), new Range(6, Double.MAX_VALUE))),
    AREA("area", List.of(new Range(0, 20), new Range(21, 50), new Range(51, 80), new Range(81, 140), new Range(141, Double.MAX_VALUE)));
    private final String code;
    private final List<Range> ranges;

    public static ValueRanges getByCode(String code) {
       return Arrays.stream(values())
                .filter(v -> v.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new UnrecognizedCodeException(code));
    }

}

@AllArgsConstructor
@Getter
class Range {
    private double min;
    private double max;
}
