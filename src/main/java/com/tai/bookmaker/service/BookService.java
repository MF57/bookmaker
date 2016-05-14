package com.tai.bookmaker.service;

import com.tai.bookmaker.domain.Book;
import com.tai.bookmaker.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Book.
 */
@Service
public class BookService {

    private final Logger log = LoggerFactory.getLogger(BookService.class);
    
    @Inject
    private BookRepository bookRepository;
    
    /**
     * Save a book.
     * 
     * @param book the entity to save
     * @return the persisted entity
     */
    public Book save(Book book) {
        log.debug("Request to save Book : {}", book);
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
}
