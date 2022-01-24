package com.hraczynski.webscrapper.fetch.providers;

public class MorizonSellOffersFetcher extends MorizonOffersFetcher {
    private static final String URL = "https://www.morizon.pl/mieszkania/krakow/";

    public MorizonSellOffersFetcher() {
        super(URL);
    }
}
