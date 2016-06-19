package com.tai.bookmaker.web.rest.dto;

import com.tai.bookmaker.domain.enumeration.MatchStatus;

import java.time.LocalDate;

/**
 * Created by mf57 on 19.06.2016.
 */
public class BookDTO {
    private String team1Name;
    private String team2Name;
    private Integer team1PredictionScore;
    private Integer team2PredictionScore;
    private Integer team1ActualScore;
    private Integer team2ActualScore;
    private LocalDate date;
    private Integer currentMinute;
    private MatchStatus status;

    public BookDTO(String team1Name, String team2Name, Integer team1PredictionScore, Integer team2PredictionScore, Integer team1ActualScore, Integer team2ActualScore, LocalDate date, Integer currentMinute, MatchStatus status) {
        this.team1Name = team1Name;
        this.team2Name = team2Name;
        this.team1PredictionScore = team1PredictionScore;
        this.team2PredictionScore = team2PredictionScore;
        this.team1ActualScore = team1ActualScore;
        this.team2ActualScore = team2ActualScore;
        this.date = date;
        this.currentMinute = currentMinute;
        this.status = status;
    }

    public String getTeam1Name() {
        return team1Name;
    }

    public String getTeam2Name() {
        return team2Name;
    }

    public Integer getTeam1PredictionScore() {
        return team1PredictionScore;
    }

    public Integer getTeam2PredictionScore() {
        return team2PredictionScore;
    }

    public Integer getTeam1ActualScore() {
        return team1ActualScore;
    }

    public Integer getTeam2ActualScore() {
        return team2ActualScore;
    }

    public LocalDate getDate() {
        return date;
    }

    public Integer getCurrentMinute() {
        return currentMinute;
    }

    public MatchStatus getStatus() {
        return status;
    }
}

