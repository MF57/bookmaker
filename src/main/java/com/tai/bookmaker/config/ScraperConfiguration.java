package com.tai.bookmaker.config;

import com.tai.bookmaker.domain.Match;
import com.tai.bookmaker.domain.Team;
import com.tai.bookmaker.domain.enumeration.MatchStatus;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

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
    public void fillDatabase() throws IOException {
        LocalDate date = LocalDate.now();
        scrapMatches(date);
    }

    private void scrapMatches(LocalDate date) throws IOException {

        Document doc = Jsoup.connect(LIVESCORE_URL+date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))).get();
        Elements elements = doc.getElementsByClass(ROW_GRAY);
        elements.forEach(element -> {
            Element time = element.getElementsByClass("min").last();
            Element team1 = element.getElementsByClass("tright").last();
            Element score = element.getElementsByClass("sco").last();
            Element team2 = element.getElementsByClass("name").last();
            processMatchData(time.text(), team1.text(), team2.text(), score.text(), date);
        });
    }

    private void processMatchData(String matchInfo, String team1Name, String team2Name, String score, LocalDate date) {
        Team team1 = findTeamInDatabase(team1Name);
        Team team2 = findTeamInDatabase(team2Name);
        Match match = findMatchInDatabase(team1.getId(), team2.getId());

        int score1 = Character.isDigit(score.charAt(0)) ? Character.getNumericValue(score.charAt(0)) : 0;
        int score2 = Character.isDigit(score.charAt(score.length() - 1)) ? Character.getNumericValue(score.charAt(score.length() - 1)) : 0;
        MatchStatus newStatus = deduceMatchStatus(matchInfo);
        int currentMinute = deduceCurrentMinute(newStatus, matchInfo);
        MatchStatus currentStatus = Optional.ofNullable(match.getStatus()).orElse(newStatus);

        match.setTeam1(team1.getId());
        match.setTeam2(team2.getId());
        match.setTeam1Score(score1);
        match.setTeam2Score(score2);
        match.setStatus(newStatus);
        match.setDate(date);
        match.setCurrentMinute(currentMinute);
        if(match.getId() == null) {
            match.setId(ObjectId.get().toHexString());
        }

        boolean updateBooks = newStatus.equals(MatchStatus.FINISHED) && currentStatus.equals(MatchStatus.IN_PROGRESS);
        boolean matchFinished = newStatus.equals(MatchStatus.FINISHED) && currentStatus.equals(MatchStatus.FINISHED);

        if(matchFinished) {
            return;
        }

        matchRepository.save(match);




    }

    private int deduceCurrentMinute(MatchStatus status, String matchInfo) {
        if(status.equals(MatchStatus.IN_FUTURE) || status.equals(MatchStatus.CANCELLED)) {
            return 0;
        } else if (status.equals(MatchStatus.FINISHED)) {
            return 90;
        } else {
            //Making 35' into 35
            String minute = matchInfo.substring(0, matchInfo.length() - 1);
            //Handling 90+2 situations
            return minute.contains("+") ? Integer.valueOf(minute.substring(0, 2)) : Integer.valueOf(minute);

        }
    }

    private MatchStatus deduceMatchStatus(String matchInfo) {
        if (matchInfo.equals("FT")) {
            return MatchStatus.FINISHED;
        } else if (matchInfo.contains("'") || matchInfo.equals("HT")) {
            return MatchStatus.IN_PROGRESS;
        } else if (matchInfo.contains(":")) {
            return MatchStatus.IN_FUTURE;
        } else {
            return MatchStatus.CANCELLED;
        }
    }

    private Team findTeamInDatabase(String teamName) {
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

    private Match findMatchInDatabase(String team1, String team2) {
        List<Match> matches = matchRepository.getMatchesByTeamsNames(team1, team2);
        if(matches == null || matches.size() == 0) {
            return new Match();
        }
        matches.sort((m1, m2) -> m1.getDate().compareTo(m2.getDate()));
        return matches.get(0);
    }

}
