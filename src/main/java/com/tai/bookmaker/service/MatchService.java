package com.tai.bookmaker.service;

import com.tai.bookmaker.domain.Match;
import com.tai.bookmaker.repository.MatchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Match.
 */
@Service
public class MatchService {

    private final Logger log = LoggerFactory.getLogger(MatchService.class);

    @Inject
    private MatchRepository matchRepository;

    /**
     * Save a match.
     *
     * @param match the entity to save
     * @return the persisted entity
     */
    public Match save(Match match) {
        log.debug("Request to save Match : {}", match);
        Match result = matchRepository.save(match);
        return result;
    }

    /**
     *  Get all the matches.
     *
     *  @return the list of entities
     */
    public List<Match> findAll() {
        log.debug("Request to get all Matches");
        List<Match> result = matchRepository.findAll();
        return result;
    }

    public List<Match> findFutureMatches() {
        log.debug("Request to get all Matches");
        List<Match> result = matchRepository.findByStatus("IN_FUTURE");
        return result;
    }

    /**
     *  Get one match by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public Match findOne(String id) {
        log.debug("Request to get Match : {}", id);
        Match match = matchRepository.findOne(id);
        return match;
    }

    /**
     *  Delete the  match by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Match : {}", id);
        matchRepository.delete(id);
    }
}
