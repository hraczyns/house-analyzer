package com.hraczynski.webscrapper.fetch.providers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Provider {
    OTODOM("Otodom"),
    MORIZON("Morizon"),
    TABELA_OFERT("Tabelaofert");

    private final String name;
}
