package com.tai.bookmaker.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Book.
 */

@Document(collection = "book")
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("user_id")
    private String userId;

    @Field("match_id")
    private String matchId;

    @Min(value = 0)
    @Field("score_1_prediction")
    private Integer score1Prediction;

    @Min(value = 0)
    @Field("score_2_prediction")
    private Integer score2Prediction;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public Integer getScore1Prediction() {
        return score1Prediction;
    }

    public void setScore1Prediction(Integer score1Prediction) {
        this.score1Prediction = score1Prediction;
    }

    public Integer getScore2Prediction() {
        return score2Prediction;
    }

    public void setScore2Prediction(Integer score2Prediction) {
        this.score2Prediction = score2Prediction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Book book = (Book) o;
        if(book.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Book{" +
            "id=" + id +
            ", userId='" + userId + "'" +
            ", matchId='" + matchId + "'" +
            ", score1Prediction='" + score1Prediction + "'" +
            ", score2Prediction='" + score2Prediction + "'" +
            '}';
    }
}
