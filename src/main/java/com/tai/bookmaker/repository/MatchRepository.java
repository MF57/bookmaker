package com.tai.bookmaker.repository;

import com.tai.bookmaker.domain.Match;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Spring Data MongoDB repository for the Match entity.
 */
public interface MatchRepository extends MongoRepository<Match, String> {

    List<Match> findByStatus(String status);

    @Query(value = "{  'team_1' : ?0, 'team_2' : ?1 }")
    List<Match> getMatchesByTeamsNames(String team1, String team2);

}
