package com.hraczynski.webscrapper;

import com.hraczynski.webscrapper.dto.*;
import com.hraczynski.webscrapper.fetch.OffersFetcherProvider;
import com.hraczynski.webscrapper.fetch.providers.Provider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class WebScrapperService {

    private static final String TRUE = Boolean.TRUE.toString();
    private final List<SingleOffer> fakeCachedOffers = new ArrayList<>();

    private final OffersFetcherProvider offerProvider;
    private final StatisticsService statisticsService;

    public List<SingleOffer> getContent(String isToCleanCached) {
        return getContent(isToCleanCached, null, null);
    }

    public List<SingleOffer> getContent(String isToCleanCached, String choice, String isPricePerMc) {
        if (Boolean.parseBoolean(isToCleanCached)) {
            fakeCachedOffers.clear();
        }
        if (fakeCachedOffers.isEmpty()) {
            fakeCachedOffers.addAll(offerProvider.getOffers());
        }

        return fakeCachedOffers
                .stream()
                .filter(getPredicateForPrices(choice, isPricePerMc))
                .collect(Collectors.toList());
    }

    public List<SingleOffer> getSortedByOrder(String order, String isPricePerMc) {
        List<SingleOffer> content = getContent(null, order, isPricePerMc);
        return content.stream()
                .sorted(Sorters.findOrderByCode(order))
                .collect(Collectors.toList());
    }

    public List<Statistics> getStatistics() {
        List<Statistics> statisticsList = new ArrayList<>();
        statisticsList.add(new Statistics("General", getForEveryType(null)));
        Arrays.stream(Provider.values()).forEach(
                provider -> statisticsList.add(new Statistics(provider.getName(), getForEveryType(provider))));
        return statisticsList;
    }

    public StatisticsPerFlat getStatisticsPerFlat(SingleOffer singleOffer) {
        double price = NumberUtils.parseNumber(singleOffer.getPrice(), true);
        double rooms = NumberUtils.parseNumber(singleOffer.getRooms(), true);
        double area = NumberUtils.parseNumber(singleOffer.getArea(), true);
        List<SingleOffer> offerList = getContent(null, Filters.BY_PRICE.getFilter(), Boolean.toString(singleOffer.getPrice().contains("/mc")));

        return statisticsService.getStatsPerFlat(offerList, price, rooms, area);
    }

    public List<GroupsOffers> getGroupsOffers(String filter, String isPricePerMc) {
        return getGroupsOffers(filter, isPricePerMc, null);
    }

    private List<GroupsOffers> getGroupsOffers(String filter, Provider provider) {
        return getGroupsOffers(filter, null, provider);
    }

    private List<GroupsOffers> getGroupsOffers(String filter, String isPricePerMc, Provider provider) {
        return statisticsService.getGroupsOffers(getContent(null, filter, isPricePerMc), filter, isPricePerMc, provider);
    }

    private List<StatisticsSection> getForEveryType(Provider provider) {
        return List.of(
                new StatisticsSection("price", getGroupsOffers("price", provider)),
                new StatisticsSection("price /mc", getGroupsOffers("price", "true", provider)),
                new StatisticsSection("rooms", getGroupsOffers("rooms", provider)),
                new StatisticsSection("area", getGroupsOffers("area", provider))
        );
    }

    private Predicate<SingleOffer> getPredicateForPrices(String choice, String isPricePerMc) {
        return offer -> {
            if (TRUE.equalsIgnoreCase(isPricePerMc) && Sorters.BY_PRICE.getOrder().equalsIgnoreCase(choice)) {
                return offer.getPrice().contains("/mc");
            } else if (Sorters.BY_PRICE.getOrder().equalsIgnoreCase(choice)) {
                return !offer.getPrice().contains("/mc");
            }
            return true;
        };
    }
}