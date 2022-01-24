package com.hraczynski.webscrapper.fetch.providers;

public class TabelaOfertHireOffersFetcher extends TabelaOfertOfferFetcher {

    private static final String URL = "https://tabelaofert.pl/mieszkania-na-wynajem/krakow";

    public TabelaOfertHireOffersFetcher() {
        super(URL,true);
    }
}
