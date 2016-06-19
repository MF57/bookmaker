package com.tai.bookmaker.web.rest.dto;

import com.tai.bookmaker.domain.enumeration.MatchStatus;

import java.time.LocalDate;

/**
 * Created by mf57 on 19.06.2016.
 */
public class MatchDTO {
    private String id;
    private LocalDate date;
    private MatchStatus status;
    private Integer currentMinute;
    private String team1;
    private String team2;
    private Integer team1Score;
    private Integer team2Score;


    public MatchDTO(String id, LocalDate date, MatchStatus status, Integer currentMinute,
                    String team1, String team2, Integer team1Score, Integer team2Score) {
        this.id = id;
        this.date = date;
        this.status = status;
        this.currentMinute = currentMinute;
        this.team1 = team1;
        this.team2 = team2;
        this.team1Score = team1Score;
        this.team2Score = team2Score;
    }

    public String getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public MatchStatus getStatus() {
        return status;
    }

    public Integer getCurrentMinute() {
        return currentMinute;
    }

    public String getTeam1() {
        return team1;
    }

    public String getTeam2() {
        return team2;
    }

    public Integer getTeam1Score() {
        return team1Score;
    }

    public Integer getTeam2Score() {
        return team2Score;
    }
}
