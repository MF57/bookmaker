package com.tai.bookmaker.service;

import com.tai.bookmaker.domain.Book;
import com.tai.bookmaker.domain.Match;
import com.tai.bookmaker.domain.Team;
import com.tai.bookmaker.repository.BookRepository;
import com.tai.bookmaker.repository.MatchRepository;
import com.tai.bookmaker.repository.TeamRepository;
import com.tai.bookmaker.web.rest.dto.MatchDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Match.
 */
@Service
public class MatchService {

    private final Logger log = LoggerFactory.getLogger(MatchService.class);

    @Inject
    private MatchRepository matchRepository;

    @Inject
    private BookRepository bookRepository;

    @Inject
    private TeamRepository teamRepository;

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

    public List<MatchDTO> findFutureMatchesForCurrentUser(String currentUserLogin) {
        log.debug("Request to get all Matches");
        List<Match> futureMatches = matchRepository.findByStatus("IN_FUTURE");
        List<MatchDTO> result = new ArrayList<>();
        for (Match match : futureMatches) {
            List<Book> usersBookForMatch = bookRepository.getUsersBookForMatch(currentUserLogin, match.getId());
            if (usersBookForMatch.size() == 0) {
                Team team1 = teamRepository.findOne(match.getTeam1());
                Team team2 = teamRepository.findOne(match.getTeam2());
                result.add(new MatchDTO(match.getId(), match.getDate(), match.getStatus(),
                    match.getCurrentMinute(), team1.getName(), team2.getName(), match.getTeam1Score(), match.getTeam2Score()));
            }
        }
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
