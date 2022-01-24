package com.hraczynski.webscrapper.fetch;

import com.hraczynski.webscrapper.dto.SingleOffer;

import java.util.List;

public interface OffersFetcher {
    List<SingleOffer> fetch();
}
