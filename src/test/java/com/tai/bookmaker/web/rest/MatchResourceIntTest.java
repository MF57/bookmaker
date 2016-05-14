package com.tai.bookmaker.web.rest;

import com.tai.bookmaker.BookmakerApp;
import com.tai.bookmaker.domain.Match;
import com.tai.bookmaker.repository.MatchRepository;
import com.tai.bookmaker.service.MatchService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tai.bookmaker.domain.enumeration.MatchStatus;

/**
 * Test class for the MatchResource REST controller.
 *
 * @see MatchResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BookmakerApp.class)
@WebAppConfiguration
@IntegrationTest
public class MatchResourceIntTest {


    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final MatchStatus DEFAULT_STATUS = MatchStatus.IN_FUTURE;
    private static final MatchStatus UPDATED_STATUS = MatchStatus.CANCELLED;

    private static final Integer DEFAULT_CURRENT_MINUTE = 0;
    private static final Integer UPDATED_CURRENT_MINUTE = 1;
    private static final String DEFAULT_TEAM_1 = "AAAAA";
    private static final String UPDATED_TEAM_1 = "BBBBB";
    private static final String DEFAULT_TEAM_2 = "AAAAA";
    private static final String UPDATED_TEAM_2 = "BBBBB";

    private static final Integer DEFAULT_TEAM_1_SCORE = 0;
    private static final Integer UPDATED_TEAM_1_SCORE = 1;

    private static final Integer DEFAULT_TEAM_2_SCORE = 0;
    private static final Integer UPDATED_TEAM_2_SCORE = 1;

    @Inject
    private MatchRepository matchRepository;

    @Inject
    private MatchService matchService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMatchMockMvc;

    private Match match;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MatchResource matchResource = new MatchResource();
        ReflectionTestUtils.setField(matchResource, "matchService", matchService);
        this.restMatchMockMvc = MockMvcBuilders.standaloneSetup(matchResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        matchRepository.deleteAll();
        match = new Match();
        match.setDate(DEFAULT_DATE);
        match.setStatus(DEFAULT_STATUS);
        match.setCurrentMinute(DEFAULT_CURRENT_MINUTE);
        match.setTeam1(DEFAULT_TEAM_1);
        match.setTeam2(DEFAULT_TEAM_2);
        match.setTeam1Score(DEFAULT_TEAM_1_SCORE);
        match.setTeam2Score(DEFAULT_TEAM_2_SCORE);
    }

    @Test
    public void createMatch() throws Exception {
        int databaseSizeBeforeCreate = matchRepository.findAll().size();

        // Create the Match

        restMatchMockMvc.perform(post("/api/matches")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(match)))
                .andExpect(status().isCreated());

        // Validate the Match in the database
        List<Match> matches = matchRepository.findAll();
        assertThat(matches).hasSize(databaseSizeBeforeCreate + 1);
        Match testMatch = matches.get(matches.size() - 1);
        assertThat(testMatch.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testMatch.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMatch.getCurrentMinute()).isEqualTo(DEFAULT_CURRENT_MINUTE);
        assertThat(testMatch.getTeam1()).isEqualTo(DEFAULT_TEAM_1);
        assertThat(testMatch.getTeam2()).isEqualTo(DEFAULT_TEAM_2);
        assertThat(testMatch.getTeam1Score()).isEqualTo(DEFAULT_TEAM_1_SCORE);
        assertThat(testMatch.getTeam2Score()).isEqualTo(DEFAULT_TEAM_2_SCORE);
    }

    @Test
    public void getAllMatches() throws Exception {
        // Initialize the database
        matchRepository.save(match);

        // Get all the matches
        restMatchMockMvc.perform(get("/api/matches?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(match.getId())))
                .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
                .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
                .andExpect(jsonPath("$.[*].currentMinute").value(hasItem(DEFAULT_CURRENT_MINUTE)))
                .andExpect(jsonPath("$.[*].team1").value(hasItem(DEFAULT_TEAM_1.toString())))
                .andExpect(jsonPath("$.[*].team2").value(hasItem(DEFAULT_TEAM_2.toString())))
                .andExpect(jsonPath("$.[*].team1Score").value(hasItem(DEFAULT_TEAM_1_SCORE)))
                .andExpect(jsonPath("$.[*].team2Score").value(hasItem(DEFAULT_TEAM_2_SCORE)));
    }

    @Test
    public void getMatch() throws Exception {
        // Initialize the database
        matchRepository.save(match);

        // Get the match
        restMatchMockMvc.perform(get("/api/matches/{id}", match.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(match.getId()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.currentMinute").value(DEFAULT_CURRENT_MINUTE))
            .andExpect(jsonPath("$.team1").value(DEFAULT_TEAM_1.toString()))
            .andExpect(jsonPath("$.team2").value(DEFAULT_TEAM_2.toString()))
            .andExpect(jsonPath("$.team1Score").value(DEFAULT_TEAM_1_SCORE))
            .andExpect(jsonPath("$.team2Score").value(DEFAULT_TEAM_2_SCORE));
    }

    @Test
    public void getNonExistingMatch() throws Exception {
        // Get the match
        restMatchMockMvc.perform(get("/api/matches/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateMatch() throws Exception {
        // Initialize the database
        matchService.save(match);

        int databaseSizeBeforeUpdate = matchRepository.findAll().size();

        // Update the match
        Match updatedMatch = new Match();
        updatedMatch.setId(match.getId());
        updatedMatch.setDate(UPDATED_DATE);
        updatedMatch.setStatus(UPDATED_STATUS);
        updatedMatch.setCurrentMinute(UPDATED_CURRENT_MINUTE);
        updatedMatch.setTeam1(UPDATED_TEAM_1);
        updatedMatch.setTeam2(UPDATED_TEAM_2);
        updatedMatch.setTeam1Score(UPDATED_TEAM_1_SCORE);
        updatedMatch.setTeam2Score(UPDATED_TEAM_2_SCORE);

        restMatchMockMvc.perform(put("/api/matches")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedMatch)))
                .andExpect(status().isOk());

        // Validate the Match in the database
        List<Match> matches = matchRepository.findAll();
        assertThat(matches).hasSize(databaseSizeBeforeUpdate);
        Match testMatch = matches.get(matches.size() - 1);
        assertThat(testMatch.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testMatch.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMatch.getCurrentMinute()).isEqualTo(UPDATED_CURRENT_MINUTE);
        assertThat(testMatch.getTeam1()).isEqualTo(UPDATED_TEAM_1);
        assertThat(testMatch.getTeam2()).isEqualTo(UPDATED_TEAM_2);
        assertThat(testMatch.getTeam1Score()).isEqualTo(UPDATED_TEAM_1_SCORE);
        assertThat(testMatch.getTeam2Score()).isEqualTo(UPDATED_TEAM_2_SCORE);
    }

    @Test
    public void deleteMatch() throws Exception {
        // Initialize the database
        matchService.save(match);

        int databaseSizeBeforeDelete = matchRepository.findAll().size();

        // Get the match
        restMatchMockMvc.perform(delete("/api/matches/{id}", match.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Match> matches = matchRepository.findAll();
        assertThat(matches).hasSize(databaseSizeBeforeDelete - 1);
    }
}
