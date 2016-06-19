package com.tai.bookmaker.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Created by mf57 on 19.06.2016.
 */
@Configuration
public class ScaperConfiguration {

    @Scheduled(fixedDelay = 1000)
    public void test() {
        System.out.println("AAAA");
    }
}
