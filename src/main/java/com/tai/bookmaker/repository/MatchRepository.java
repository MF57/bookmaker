package com.tai.bookmaker.repository;

import com.tai.bookmaker.domain.Match;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Spring Data MongoDB repository for the Match entity.
 */
public interface MatchRepository extends MongoRepository<Match,String> {

    List<Match> findByStatus(String status);

}
