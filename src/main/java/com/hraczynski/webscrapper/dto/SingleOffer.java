package com.hraczynski.webscrapper.dto;

import com.hraczynski.webscrapper.fetch.providers.Provider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class SingleOffer {
    private String title;
    private String area;
    private String price;
    private String rooms;
    private String imgUrl;
    private String offerUrl;
    private String provider;
}
