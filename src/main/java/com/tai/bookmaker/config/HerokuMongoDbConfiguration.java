package com.tai.bookmaker.config;

import com.mongodb.DB;
import com.mongodb.MongoException;
import com.mongodb.MongoURI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.net.UnknownHostException;

@Configuration
@Profile(Constants.SPRING_PROFILE_HEROKU)
public class HerokuMongoDbConfiguration {
    @Bean
    public DB getDb() throws UnknownHostException, MongoException {
        MongoURI mongoURI = new MongoURI(System.getenv("MONGOHQ_URL"));
        DB db = mongoURI.connectDB();
        db.authenticate(mongoURI.getUsername(), mongoURI.getPassword());

        return db;
    }
}
