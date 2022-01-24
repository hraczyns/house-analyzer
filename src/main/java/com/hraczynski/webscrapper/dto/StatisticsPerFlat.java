package com.hraczynski.webscrapper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StatisticsPerFlat {
    private double averagePricePerMeter;
    private double pricePerMeter;
    private double averagePriceForFlatWithAboutInputMeters;
    private double averagePriceForFlatWithAboutInputRooms;

}
