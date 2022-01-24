package com.hraczynski.webscrapper.fetch;

import com.hraczynski.webscrapper.dto.SingleOffer;
import com.hraczynski.webscrapper.fetch.providers.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class OffersFetcherProvider {
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public static OffersFetcher[] getFetchers() {
        return new OffersFetcher[]{
                new OtodomOffersFetcher(),
                new TabelaOfertHireOffersFetcher(),
                new TabelaOfertSellOffersFetcher(),
                new MorizonSellOffersFetcher(),
                new MorizonHireOffersFetcher()
        };
    }

    public List<SingleOffer> getOffers() {
        List<SingleOffer> offers = Collections.synchronizedList(new ArrayList<>());
        List<Callable<List<SingleOffer>>> callableList = Arrays.stream(getFetchers())
                .map(fetcher -> (Callable<List<SingleOffer>>) fetcher::fetch)
                .collect(Collectors.toList());
        try {
            executorService.invokeAll(callableList)
                    .forEach(task -> {
                        try {
                            offers.addAll(task.get());
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (InterruptedException ignored) {
        }
        return offers;
    }
}
