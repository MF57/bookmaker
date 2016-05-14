package com.tai.bookmaker.repository;

import com.tai.bookmaker.domain.Team;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Team entity.
 */
public interface TeamRepository extends MongoRepository<Team,String> {

}
