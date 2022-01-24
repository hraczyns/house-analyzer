package com.hraczynski.webscrapper.fetch.providers;

import com.hraczynski.webscrapper.dto.SingleOffer;
import com.hraczynski.webscrapper.fetch.AbstractOfferFetcher;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class MorizonOffersFetcher extends AbstractOfferFetcher {

    private final String url;
    private boolean addPerMc;

    public MorizonOffersFetcher(String url) {
        this.url = url;
    }

    public MorizonOffersFetcher(String url, boolean addPerMc) {
        this(url);
        this.addPerMc = addPerMc;
    }

    @Override
    public List<SingleOffer> fetch() {
        List<SingleOffer> offers = new ArrayList<>();
        Document document = connectAndGetStructure(url);
        Elements elements = document.getElementsByTag("section").get(3).getElementsByClass("mz-card__item");
        for (Element element : elements) {
            try {
                String title = element.getElementsByClass("single-result__title").text();
                String price = element.getElementsByClass("single-result__price").get(0).text();
                if (addPerMc) {
                    price += " /mc";
                }
                String imgUrl = element.getElementsByClass("mz-card__img").get(0).getElementsByClass("js-ignore-dppx").attr("data-original");
                if (imgUrl.isEmpty()) {
                    imgUrl = element.getElementsByClass("mz-card__img").get(0).getElementsByClass("js-ignore-dppx").attr("src");
                }
                Elements detailsContainer = element.getElementsByClass("param list-unstyled list-inline").get(0).getAllElements();
                String rooms = detailsContainer.get(1).text();
                String area = detailsContainer.get(3).text();
                String offerUrl = element.getElementsByTag("a").get(0).attr("href");
                offers.add(new SingleOffer(title, area, price, rooms, imgUrl, offerUrl,Provider.MORIZON.getName()));
            } catch (RuntimeException ignored) {
            }
        }
        return offers;
    }
}
