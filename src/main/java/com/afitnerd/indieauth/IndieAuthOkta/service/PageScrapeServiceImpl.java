package com.afitnerd.indieauth.IndieAuthOkta.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;

@Service
public class PageScrapeServiceImpl implements PageScrapeService {

    @Override
    public String findAuthorizationTag(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements linkRels = doc.select("link[rel]");
        Element linkRel = linkRels.stream()
            .filter(elem -> REL_AUTHORIZATION_ENDPOINT.equals(elem.attr("rel")))
            .findFirst()
            .orElseThrow(
                () -> new RuntimeException("Didn't find rel=\"" + REL_AUTHORIZATION_ENDPOINT + "\" in any link tags")
            );
        String href = linkRel.attr("href");
        Assert.hasText(href, "href must not be empty. Found: " + linkRel.text());
        return href;
    }
}
