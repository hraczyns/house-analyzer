package com.hraczynski.webscrapper.fetch.providers;

import com.hraczynski.webscrapper.dto.SingleOffer;
import com.hraczynski.webscrapper.fetch.AbstractOfferFetcher;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class TabelaOfertOfferFetcher extends AbstractOfferFetcher {

    private static final String HREF_PREFIX = "https://tabelaofert.pl";
    private static final String INVALID_IMG_URL = "/images/to/other/blank_offer_min.jpg";

    private final String url;
    private boolean addPerMc;

    public TabelaOfertOfferFetcher(String url) {
        this.url = url;
    }

    public TabelaOfertOfferFetcher(String url, boolean addPerMc) {
        this(url);
        this.addPerMc = addPerMc;
    }

    @Override
    public List<SingleOffer> fetch() {
        Document document = connectAndGetStructure(url);
        List<SingleOffer> offers = new ArrayList<>();
        Elements elements = document.getElementsByClass("oferta-box__body");
        for (Element element : elements) {
            try {
                Element urlContainer = element.getElementsByClass("oferta-box__image image").get(0);
                String offerUrl = HREF_PREFIX + urlContainer.getElementsByTag("a").attr("href");
                String imgUrl = urlContainer.getElementsByTag("img").attr("src");
                if (INVALID_IMG_URL.equalsIgnoreCase(imgUrl)) {
                    imgUrl = urlContainer.getElementsByTag("img").attr("data-src");
                }
                Elements detailsContainerList = element.getElementsByClass("dane__opis");
                String rooms = detailsContainerList.get(0).text();
                String area = detailsContainerList.get(1).text();
                String price = element.getElementsByClass("image__badge cena").text();
                String title = element.getElementsByClass("title__invest").text();
                if (addPerMc) {
                    price += " /mc";
                }
                offers.add(new SingleOffer(title, area, price, rooms, imgUrl, offerUrl, Provider.TABELA_OFERT.getName()));
            } catch (RuntimeException ignored) {
            }
        }
        return offers;
    }
}
