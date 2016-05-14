package com.tai.bookmaker.repository;

import com.tai.bookmaker.domain.Match;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Match entity.
 */
public interface MatchRepository extends MongoRepository<Match,String> {

}
