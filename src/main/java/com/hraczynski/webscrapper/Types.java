package com.hraczynski.webscrapper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Types {
    PRICE("price"),
    PRICE_MC("price /mc"),
    ROOMS("rooms"),
    AREA("area");

    private final String type;

}
