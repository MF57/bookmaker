package com.tai.bookmaker.config;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;

/**
 * Created by mf57 on 19.06.2016.
 */
@Configuration
public class ScaperConfiguration {

    @Scheduled(initialDelay = 10000, fixedDelay = 60000)
    public void test() throws IOException {
        Document doc = Jsoup.connect("http://www.livescore.com/soccer/").get();
        System.out.println(doc.toString());
    }
}
