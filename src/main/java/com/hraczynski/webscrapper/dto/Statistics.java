package com.hraczynski.webscrapper.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public class Statistics {
    private final String providerName;
    private final List<StatisticsSection> statisticsSections;
}
