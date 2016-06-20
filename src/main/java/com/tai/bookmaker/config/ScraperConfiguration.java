package com.tai.bookmaker.config;

import com.tai.bookmaker.domain.Team;
import com.tai.bookmaker.repository.BookRepository;
import com.tai.bookmaker.repository.MatchRepository;
import com.tai.bookmaker.repository.TeamRepository;
import org.bson.types.ObjectId;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;

/**
 * Created by mf57 on 19.06.2016.
 */
@Configuration
public class ScraperConfiguration {

    private static final String LIVESCORE_URL = "http://www.livescore.com/soccer/";
    private static final String ROW_GRAY = "row-gray";
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MatchRepository matchRepository;


    @Scheduled(initialDelay = 10, fixedDelay = 60000)
    public void test() throws IOException {
        Document doc = Jsoup.connect(LIVESCORE_URL).get();
        Elements elements = doc.getElementsByClass(ROW_GRAY);
        elements.forEach(element -> {
            Element time = element.getElementsByClass("min").last();
            Element team1 = element.getElementsByClass("tright").last();
            Element score = element.getElementsByClass("sco").last();
            Element team2 = element.getElementsByClass("name").last();
            parseMatch(time.text(), team1.text(), team2.text(), score.text());
        });

    }

    private void parseMatch(String matchInfo, String team1Name, String team2Name, String score) {
        Team team1 = parseTeam(team1Name);
        Team team2 = parseTeam(team2Name);
    }

    private Team parseTeam(String teamName) {
        Team team = teamRepository.findByName(teamName);
        if(team == null) {
            team = new Team();
            team.setId(ObjectId.get().toHexString());
            team.setName(teamName);
            team.setNumberOfDraws(0);
            team.setNumberOfWonMatches(0);
            team.setNumberOfLostMatches(0);
            teamRepository.save(team);
        }
        return team;
    }
}
