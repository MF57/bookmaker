package com.tai.bookmaker.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Team.
 */

@Document(collection = "team")
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("name")
    private String name;

    @Min(value = 0)
    @Field("number_of_won_matches")
    private Integer numberOfWonMatches;

    @Min(value = 0)
    @Field("number_of_lost_matches")
    private Integer numberOfLostMatches;

    @Min(value = 0)
    @Field("number_of_draws")
    private Integer numberOfDraws;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumberOfWonMatches() {
        return numberOfWonMatches;
    }

    public void setNumberOfWonMatches(Integer numberOfWonMatches) {
        this.numberOfWonMatches = numberOfWonMatches;
    }

    public Integer getNumberOfLostMatches() {
        return numberOfLostMatches;
    }

    public void setNumberOfLostMatches(Integer numberOfLostMatches) {
        this.numberOfLostMatches = numberOfLostMatches;
    }

    public Integer getNumberOfDraws() {
        return numberOfDraws;
    }

    public void setNumberOfDraws(Integer numberOfDraws) {
        this.numberOfDraws = numberOfDraws;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Team team = (Team) o;
        if(team.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, team.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Team{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", numberOfWonMatches='" + numberOfWonMatches + "'" +
            ", numberOfLostMatches='" + numberOfLostMatches + "'" +
            ", numberOfDraws='" + numberOfDraws + "'" +
            '}';
    }
}
