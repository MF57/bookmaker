package com.tai.bookmaker.service;

import com.tai.bookmaker.domain.Team;
import com.tai.bookmaker.repository.TeamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Team.
 */
@Service
public class TeamService {

    private final Logger log = LoggerFactory.getLogger(TeamService.class);
    
    @Inject
    private TeamRepository teamRepository;
    
    /**
     * Save a team.
     * 
     * @param team the entity to save
     * @return the persisted entity
     */
    public Team save(Team team) {
        log.debug("Request to save Team : {}", team);
        Team result = teamRepository.save(team);
        return result;
    }

    /**
     *  Get all the teams.
     *  
     *  @return the list of entities
     */
    public List<Team> findAll() {
        log.debug("Request to get all Teams");
        List<Team> result = teamRepository.findAll();
        return result;
    }

    /**
     *  Get one team by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public Team findOne(String id) {
        log.debug("Request to get Team : {}", id);
        Team team = teamRepository.findOne(id);
        return team;
    }

    /**
     *  Delete the  team by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Team : {}", id);
        teamRepository.delete(id);
    }
}
