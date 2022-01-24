package com.hraczynski.webscrapper.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class StatisticsSection {
    private final String title;
    private final List<GroupsOffers> groupsOffersList;
}
