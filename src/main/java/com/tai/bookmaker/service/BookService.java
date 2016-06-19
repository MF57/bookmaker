package com.tai.bookmaker.service;

import com.tai.bookmaker.domain.Book;
import com.tai.bookmaker.domain.Match;
import com.tai.bookmaker.domain.Team;
import com.tai.bookmaker.domain.enumeration.BookStatus;
import com.tai.bookmaker.repository.BookRepository;
import com.tai.bookmaker.repository.MatchRepository;
import com.tai.bookmaker.repository.TeamRepository;
import com.tai.bookmaker.web.rest.dto.BookDTO;
import com.tai.bookmaker.web.rest.dto.UserBookInfoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Book.
 */
@Service
public class BookService {

    private final Logger log = LoggerFactory.getLogger(BookService.class);

    @Inject
    private BookRepository bookRepository;

    @Inject
    private MatchRepository matchRepository;

    @Inject
    private TeamRepository teamRepository;

    /**
     * Save a book.
     *
     * @param book the entity to save
     * @return the persisted entity
     */
    public Book save(Book book) {
        log.debug("Request to save Book : {}", book);
        if(book.getBookStatus() == null) {
            book.setBookStatus(BookStatus.PENDING);
        }
        Book result = bookRepository.save(book);
        return result;
    }

    /**
     *  Get all the books.
     *
     *  @return the list of entities
     */
    public List<Book> findAll() {
        log.debug("Request to get all Books");
        List<Book> result = bookRepository.findAll();
        return result;
    }

    /**
     *  Get one book by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public Book findOne(String id) {
        log.debug("Request to get Book : {}", id);
        Book book = bookRepository.findOne(id);
        return book;
    }

    /**
     *  Delete the  book by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Book : {}", id);
        bookRepository.delete(id);
    }


    public UserBookInfoDTO findUserBookInfo(String currentUserLogin) {
        List<Book> userBooks = bookRepository.findByUserId(currentUserLogin);
        List<Book> wonBooks = userBooks.stream()
            .filter(book -> book.getBookStatus().equals(BookStatus.WON)).collect(Collectors.toList());
        List<Book> lostBooks = userBooks.stream()
            .filter(book -> book.getBookStatus().equals(BookStatus.LOST)).collect(Collectors.toList());
        List<Book> pendingBooks = userBooks.stream()
            .filter(book -> book.getBookStatus().equals(BookStatus.PENDING)).collect(Collectors.toList());
        List<BookDTO> wonBookDTOs = createBookDTOs(wonBooks);
        List<BookDTO> lostBookDTOs = createBookDTOs(lostBooks);
        List<BookDTO> pendingBookDTOs = createBookDTOs(pendingBooks);

        return new UserBookInfoDTO(wonBookDTOs, lostBookDTOs, pendingBookDTOs);
    }

    private List<BookDTO> createBookDTOs(List<Book> books) {
        List<BookDTO> result = new ArrayList<>();
        for (Book book : books) {
            Match match = matchRepository.findOne(book.getMatchId());
            Team team1 = teamRepository.findOne(match.getTeam1());
            Team team2 = teamRepository.findOne(match.getTeam2());
            result.add(new BookDTO(team1.getName(), team2.getName(), book.getScore1Prediction(),
                book.getScore2Prediction(), match.getTeam1Score(), match.getTeam2Score(), match.getDate(),
                match.getCurrentMinute(), match.getStatus()));
        }
        return result;

    }
}
