package com.hraczynski.webscrapper.fetch.providers;

public class TabelaOfertSellOffersFetcher extends TabelaOfertOfferFetcher {

    private static final String URL = "https://tabelaofert.pl/mieszkania-na-sprzedaz/krakow";

    public TabelaOfertSellOffersFetcher() {
        super(URL);
    }
}
