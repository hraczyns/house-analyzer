package com.hraczynski.webscrapper.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
@SuppressWarnings("all")
public class GroupsOffers {
    private final List<SingleOffer> singleOffers;
    private final String title;
    private final double min;
    private final double max;
}
