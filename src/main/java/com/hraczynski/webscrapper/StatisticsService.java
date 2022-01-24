package com.hraczynski.webscrapper;

import com.hraczynski.webscrapper.dto.GroupsOffers;
import com.hraczynski.webscrapper.dto.SingleOffer;
import com.hraczynski.webscrapper.dto.StatisticsPerFlat;
import com.hraczynski.webscrapper.fetch.providers.Provider;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class StatisticsService {

    private static final String TRUE = Boolean.TRUE.toString();

    public List<GroupsOffers> getGroupsOffers(List<SingleOffer> offerList, String filter, String isPricePerMc, Provider provider) {
        List<GroupsOffers> groupsOffers = new ArrayList<>();

        List<SingleOffer> content = offerList
                .stream()
                .filter(getPredicateForProviders(provider))
                .collect(Collectors.toList());

        ValueRanges valueRanges;
        if (TRUE.equalsIgnoreCase(isPricePerMc) && ValueRanges.PRICE.getCode().equalsIgnoreCase(filter)) {
            valueRanges = ValueRanges.PRICE_MC;
        } else {
            valueRanges = ValueRanges.getByCode(filter);
        }

        List<Range> ranges = valueRanges.getRanges();
        ranges.forEach(range -> {
            GroupsOffers groupsOffer = new GroupsOffers(new ArrayList<>(), filter, range.getMin(), range.getMax());
            content.forEach(offer -> {
                Function<SingleOffer, String> mapperByFilter = Filters.getMapperByFilter(filter);
                double result = NumberUtils.parseNumber(mapperByFilter.apply(offer), true);
                if (range.getMin() <= result && range.getMax() >= result) {
                    groupsOffer.getSingleOffers().add(offer);
                }
            });
            groupsOffers.add(groupsOffer);
        });
        return groupsOffers;
    }

    public StatisticsPerFlat getStatsPerFlat(List<SingleOffer> content, double price, double rooms, double area) {
        double averagePricePerMeter = getAveragePricePerMeter(content);
        double pricePerMeter = price / area;
        double averagePriceForFlatWithAboutInputMeters = getAveragePriceForFlatWithAboutInputMeters(content, area);
        double averagePriceForFlatWithAboutInputRooms = getAveragePriceForFlatWithAboutInputRooms(content, rooms);
        return new StatisticsPerFlat(averagePricePerMeter, pricePerMeter, averagePriceForFlatWithAboutInputMeters, averagePriceForFlatWithAboutInputRooms);
    }

    private Predicate<SingleOffer> getPredicateForProviders(Provider provider) {
        return offer -> {
            if (provider != null) {
                return offer.getProvider().equalsIgnoreCase(provider.getName());
            }
            return true;
        };
    }

    private double getAveragePricePerMeter(List<SingleOffer> content) {
        double total = 0;
        for (SingleOffer singleOffer : content) {
            double price = NumberUtils.parseNumber(singleOffer.getPrice(), true);
            double area = NumberUtils.parseNumber(singleOffer.getArea(), true);
            total += (price / area);
        }
        return total / content.size();
    }

    private double getAveragePrice(List<SingleOffer> content) {
        double total = 0;
        for (SingleOffer singleOffer : content) {
            double price = NumberUtils.parseNumber(singleOffer.getPrice(), true);
            total += price;
        }
        return total / content.size();
    }

    private double getAveragePriceForFlatWithAboutInputRooms(List<SingleOffer> content, double rooms) {
        content = content.stream()
                .filter(offer -> {
                    double offerRooms = NumberUtils.parseNumber(offer.getRooms(), true);
                    return offerRooms == rooms;
                })
                .collect(Collectors.toList());
        return getAveragePrice(content);
    }

    private double getAveragePriceForFlatWithAboutInputMeters(List<SingleOffer> content, double area) {
        content = content.stream()
                .filter(offer -> {
                    double offerArea = NumberUtils.parseNumber(offer.getArea(), true);
                    return offerArea >= area - 5 && offerArea <= area + 5;
                })
                .collect(Collectors.toList());

        return getAveragePrice(content);
    }
}
