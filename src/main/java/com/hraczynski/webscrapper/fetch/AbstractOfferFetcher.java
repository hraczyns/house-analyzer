package com.hraczynski.webscrapper.fetch;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

@Slf4j
public abstract class AbstractOfferFetcher implements OffersFetcher {

    protected Document connectAndGetStructure(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            log.warn("Cannot connect to {}", url);
            throw new IllegalStateException("Cannot connect to " + url);
        }
    }
}
