package com.tai.bookmaker.web.rest;

import com.tai.bookmaker.BookmakerApp;
import com.tai.bookmaker.domain.Team;
import com.tai.bookmaker.repository.TeamRepository;
import com.tai.bookmaker.service.TeamService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the TeamResource REST controller.
 *
 * @see TeamResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BookmakerApp.class)
@WebAppConfiguration
@IntegrationTest
public class TeamResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final Integer DEFAULT_NUMBER_OF_WON_MATCHES = 1;
    private static final Integer UPDATED_NUMBER_OF_WON_MATCHES = 2;

    private static final Integer DEFAULT_NUMBER_OF_LOST_MATCHES = 0;
    private static final Integer UPDATED_NUMBER_OF_LOST_MATCHES = 1;

    private static final Integer DEFAULT_NUMBER_OF_DRAWS = 0;
    private static final Integer UPDATED_NUMBER_OF_DRAWS = 1;

    @Inject
    private TeamRepository teamRepository;

    @Inject
    private TeamService teamService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTeamMockMvc;

    private Team team;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TeamResource teamResource = new TeamResource();
        ReflectionTestUtils.setField(teamResource, "teamService", teamService);
        this.restTeamMockMvc = MockMvcBuilders.standaloneSetup(teamResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        teamRepository.deleteAll();
        team = new Team();
        team.setName(DEFAULT_NAME);
        team.setNumberOfWonMatches(DEFAULT_NUMBER_OF_WON_MATCHES);
        team.setNumberOfLostMatches(DEFAULT_NUMBER_OF_LOST_MATCHES);
        team.setNumberOfDraws(DEFAULT_NUMBER_OF_DRAWS);
    }

    @Test
    public void createTeam() throws Exception {
        int databaseSizeBeforeCreate = teamRepository.findAll().size();

        // Create the Team

        restTeamMockMvc.perform(post("/api/teams")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(team)))
                .andExpect(status().isCreated());

        // Validate the Team in the database
        List<Team> teams = teamRepository.findAll();
        assertThat(teams).hasSize(databaseSizeBeforeCreate + 1);
        Team testTeam = teams.get(teams.size() - 1);
        assertThat(testTeam.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTeam.getNumberOfWonMatches()).isEqualTo(DEFAULT_NUMBER_OF_WON_MATCHES);
        assertThat(testTeam.getNumberOfLostMatches()).isEqualTo(DEFAULT_NUMBER_OF_LOST_MATCHES);
        assertThat(testTeam.getNumberOfDraws()).isEqualTo(DEFAULT_NUMBER_OF_DRAWS);
    }

    @Test
    public void getAllTeams() throws Exception {
        // Initialize the database
        teamRepository.save(team);

        // Get all the teams
        restTeamMockMvc.perform(get("/api/teams?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(team.getId())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].numberOfWonMatches").value(hasItem(DEFAULT_NUMBER_OF_WON_MATCHES)))
                .andExpect(jsonPath("$.[*].numberOfLostMatches").value(hasItem(DEFAULT_NUMBER_OF_LOST_MATCHES)))
                .andExpect(jsonPath("$.[*].numberOfDraws").value(hasItem(DEFAULT_NUMBER_OF_DRAWS)));
    }

    @Test
    public void getTeam() throws Exception {
        // Initialize the database
        teamRepository.save(team);

        // Get the team
        restTeamMockMvc.perform(get("/api/teams/{id}", team.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(team.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.numberOfWonMatches").value(DEFAULT_NUMBER_OF_WON_MATCHES))
            .andExpect(jsonPath("$.numberOfLostMatches").value(DEFAULT_NUMBER_OF_LOST_MATCHES))
            .andExpect(jsonPath("$.numberOfDraws").value(DEFAULT_NUMBER_OF_DRAWS));
    }

    @Test
    public void getNonExistingTeam() throws Exception {
        // Get the team
        restTeamMockMvc.perform(get("/api/teams/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateTeam() throws Exception {
        // Initialize the database
        teamService.save(team);

        int databaseSizeBeforeUpdate = teamRepository.findAll().size();

        // Update the team
        Team updatedTeam = new Team();
        updatedTeam.setId(team.getId());
        updatedTeam.setName(UPDATED_NAME);
        updatedTeam.setNumberOfWonMatches(UPDATED_NUMBER_OF_WON_MATCHES);
        updatedTeam.setNumberOfLostMatches(UPDATED_NUMBER_OF_LOST_MATCHES);
        updatedTeam.setNumberOfDraws(UPDATED_NUMBER_OF_DRAWS);

        restTeamMockMvc.perform(put("/api/teams")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedTeam)))
                .andExpect(status().isOk());

        // Validate the Team in the database
        List<Team> teams = teamRepository.findAll();
        assertThat(teams).hasSize(databaseSizeBeforeUpdate);
        Team testTeam = teams.get(teams.size() - 1);
        assertThat(testTeam.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTeam.getNumberOfWonMatches()).isEqualTo(UPDATED_NUMBER_OF_WON_MATCHES);
        assertThat(testTeam.getNumberOfLostMatches()).isEqualTo(UPDATED_NUMBER_OF_LOST_MATCHES);
        assertThat(testTeam.getNumberOfDraws()).isEqualTo(UPDATED_NUMBER_OF_DRAWS);
    }

    @Test
    public void deleteTeam() throws Exception {
        // Initialize the database
        teamService.save(team);

        int databaseSizeBeforeDelete = teamRepository.findAll().size();

        // Get the team
        restTeamMockMvc.perform(delete("/api/teams/{id}", team.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Team> teams = teamRepository.findAll();
        assertThat(teams).hasSize(databaseSizeBeforeDelete - 1);
    }
}
