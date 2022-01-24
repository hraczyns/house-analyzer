package com.hraczynski.webscrapper.fetch.providers;

public class MorizonHireOffersFetcher extends MorizonOffersFetcher {
    private static final String URL = "https://www.morizon.pl/do-wynajecia/mieszkania/krakow/";

    public MorizonHireOffersFetcher() {
        super(URL, true);
    }
}
