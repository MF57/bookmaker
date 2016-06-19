package com.tai.bookmaker.web.rest;

import com.tai.bookmaker.BookmakerApp;
import com.tai.bookmaker.domain.Book;
import com.tai.bookmaker.repository.BookRepository;
import com.tai.bookmaker.service.BookService;

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

import com.tai.bookmaker.domain.enumeration.BookStatus;

/**
 * Test class for the BookResource REST controller.
 *
 * @see BookResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BookmakerApp.class)
@WebAppConfiguration
@IntegrationTest
public class BookResourceIntTest {

    private static final String DEFAULT_USER_ID = "AAAAA";
    private static final String UPDATED_USER_ID = "BBBBB";
    private static final String DEFAULT_MATCH_ID = "AAAAA";
    private static final String UPDATED_MATCH_ID = "BBBBB";

    private static final Integer DEFAULT_SCORE_1_PREDICTION = 0;
    private static final Integer UPDATED_SCORE_1_PREDICTION = 1;

    private static final Integer DEFAULT_SCORE_2_PREDICTION = 1;
    private static final Integer UPDATED_SCORE_2_PREDICTION = 2;

    private static final BookStatus DEFAULT_BOOK_STATUS = BookStatus.WON;
    private static final BookStatus UPDATED_BOOK_STATUS = BookStatus.LOST;

    @Inject
    private BookRepository bookRepository;

    @Inject
    private BookService bookService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restBookMockMvc;

    private Book book;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BookResource bookResource = new BookResource();
        ReflectionTestUtils.setField(bookResource, "bookService", bookService);
        this.restBookMockMvc = MockMvcBuilders.standaloneSetup(bookResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        bookRepository.deleteAll();
        book = new Book();
        book.setUserId(DEFAULT_USER_ID);
        book.setMatchId(DEFAULT_MATCH_ID);
        book.setScore1Prediction(DEFAULT_SCORE_1_PREDICTION);
        book.setScore2Prediction(DEFAULT_SCORE_2_PREDICTION);
        book.setBookStatus(DEFAULT_BOOK_STATUS);
    }

    @Test
    public void createBook() throws Exception {
        int databaseSizeBeforeCreate = bookRepository.findAll().size();

        // Create the Book

        restBookMockMvc.perform(post("/api/books")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(book)))
                .andExpect(status().isCreated());

        // Validate the Book in the database
        List<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(databaseSizeBeforeCreate + 1);
        Book testBook = books.get(books.size() - 1);
        assertThat(testBook.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testBook.getMatchId()).isEqualTo(DEFAULT_MATCH_ID);
        assertThat(testBook.getScore1Prediction()).isEqualTo(DEFAULT_SCORE_1_PREDICTION);
        assertThat(testBook.getScore2Prediction()).isEqualTo(DEFAULT_SCORE_2_PREDICTION);
        assertThat(testBook.getBookStatus()).isEqualTo(DEFAULT_BOOK_STATUS);
    }

    @Test
    public void getAllBooks() throws Exception {
        // Initialize the database
        bookRepository.save(book);

        // Get all the books
        restBookMockMvc.perform(get("/api/books?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(book.getId())))
                .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.toString())))
                .andExpect(jsonPath("$.[*].matchId").value(hasItem(DEFAULT_MATCH_ID.toString())))
                .andExpect(jsonPath("$.[*].score1Prediction").value(hasItem(DEFAULT_SCORE_1_PREDICTION)))
                .andExpect(jsonPath("$.[*].score2Prediction").value(hasItem(DEFAULT_SCORE_2_PREDICTION)))
                .andExpect(jsonPath("$.[*].bookStatus").value(hasItem(DEFAULT_BOOK_STATUS.toString())));
    }

    @Test
    public void getBook() throws Exception {
        // Initialize the database
        bookRepository.save(book);

        // Get the book
        restBookMockMvc.perform(get("/api/books/{id}", book.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(book.getId()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.toString()))
            .andExpect(jsonPath("$.matchId").value(DEFAULT_MATCH_ID.toString()))
            .andExpect(jsonPath("$.score1Prediction").value(DEFAULT_SCORE_1_PREDICTION))
            .andExpect(jsonPath("$.score2Prediction").value(DEFAULT_SCORE_2_PREDICTION))
            .andExpect(jsonPath("$.bookStatus").value(DEFAULT_BOOK_STATUS.toString()));
    }

    @Test
    public void getNonExistingBook() throws Exception {
        // Get the book
        restBookMockMvc.perform(get("/api/books/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateBook() throws Exception {
        // Initialize the database
        bookService.save(book);

        int databaseSizeBeforeUpdate = bookRepository.findAll().size();

        // Update the book
        Book updatedBook = new Book();
        updatedBook.setId(book.getId());
        updatedBook.setUserId(UPDATED_USER_ID);
        updatedBook.setMatchId(UPDATED_MATCH_ID);
        updatedBook.setScore1Prediction(UPDATED_SCORE_1_PREDICTION);
        updatedBook.setScore2Prediction(UPDATED_SCORE_2_PREDICTION);
        updatedBook.setBookStatus(UPDATED_BOOK_STATUS);

        restBookMockMvc.perform(put("/api/books")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedBook)))
                .andExpect(status().isOk());

        // Validate the Book in the database
        List<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(databaseSizeBeforeUpdate);
        Book testBook = books.get(books.size() - 1);
        assertThat(testBook.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testBook.getMatchId()).isEqualTo(UPDATED_MATCH_ID);
        assertThat(testBook.getScore1Prediction()).isEqualTo(UPDATED_SCORE_1_PREDICTION);
        assertThat(testBook.getScore2Prediction()).isEqualTo(UPDATED_SCORE_2_PREDICTION);
        assertThat(testBook.getBookStatus()).isEqualTo(UPDATED_BOOK_STATUS);
    }

    @Test
    public void deleteBook() throws Exception {
        // Initialize the database
        bookService.save(book);

        int databaseSizeBeforeDelete = bookRepository.findAll().size();

        // Get the book
        restBookMockMvc.perform(delete("/api/books/{id}", book.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(databaseSizeBeforeDelete - 1);
    }
}
