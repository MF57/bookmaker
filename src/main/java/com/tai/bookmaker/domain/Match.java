package com.tai.bookmaker.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.tai.bookmaker.domain.enumeration.MatchStatus;

/**
 * A Match.
 */

@Document(collection = "match")
public class Match implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("date")
    private LocalDate date;

    @Field("status")
    private MatchStatus status;

    @Min(value = 0)
    @Max(value = 200)
    @Field("current_minute")
    private Integer currentMinute;

    @Field("team_1")
    private String team1;

    @Field("team_2")
    private String team2;

    @Min(value = 0)
    @Field("team_1_score")
    private Integer team1Score;

    @Min(value = 0)
    @Field("team_2_score")
    private Integer team2Score;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public MatchStatus getStatus() {
        return status;
    }

    public void setStatus(MatchStatus status) {
        this.status = status;
    }

    public Integer getCurrentMinute() {
        return currentMinute;
    }

    public void setCurrentMinute(Integer currentMinute) {
        this.currentMinute = currentMinute;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public Integer getTeam1Score() {
        return team1Score;
    }

    public void setTeam1Score(Integer team1Score) {
        this.team1Score = team1Score;
    }

    public Integer getTeam2Score() {
        return team2Score;
    }

    public void setTeam2Score(Integer team2Score) {
        this.team2Score = team2Score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Match match = (Match) o;
        if(match.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, match.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Match{" +
            "id=" + id +
            ", date='" + date + "'" +
            ", status='" + status + "'" +
            ", currentMinute='" + currentMinute + "'" +
            ", team1='" + team1 + "'" +
            ", team2='" + team2 + "'" +
            ", team1Score='" + team1Score + "'" +
            ", team2Score='" + team2Score + "'" +
            '}';
    }
}
