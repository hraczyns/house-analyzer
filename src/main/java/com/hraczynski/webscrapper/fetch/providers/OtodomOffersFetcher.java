package com.hraczynski.webscrapper.fetch.providers;

import com.hraczynski.webscrapper.dto.SingleOffer;
import com.hraczynski.webscrapper.fetch.AbstractOfferFetcher;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class OtodomOffersFetcher extends AbstractOfferFetcher {

    private static final String URL = "https://www.otodom.pl/oferty/?search%5Bhomepage_ads%5D=1";

    @Override
    public List<SingleOffer> fetch() {
        Document document = connectAndGetStructure(URL);
        List<SingleOffer> offerList = new ArrayList<>();
        Elements offers = document.getElementsByClass("offer-item");
        for (Element singleOffer : offers) {
            String title = singleOffer.getElementsByClass("offer-item-title").text();
            String price = singleOffer.getElementsByClass("offer-item-price").text();
            String area = singleOffer.getElementsByClass("offer-item-area").text();
            String rooms = singleOffer.getElementsByClass("offer-item-rooms").text();
            String imgUrl = singleOffer.getElementsByClass("img-cover").attr("data-src");
            String offerUrl = singleOffer.getElementsByAttributeValue("data-tracking-data", "{\"touch_point_button\":\"photo\"}").attr("href");
            offerList.add(new SingleOffer(title, area, price, rooms, imgUrl, offerUrl,Provider.OTODOM.getName()));
        }
        return offerList;
    }
}
