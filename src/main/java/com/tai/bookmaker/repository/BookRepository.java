package com.tai.bookmaker.repository;

import com.tai.bookmaker.domain.Book;

import com.tai.bookmaker.domain.Match;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Spring Data MongoDB repository for the Book entity.
 */
public interface BookRepository extends MongoRepository<Book,String> {

    @Query(value = "{  'userId' : ?0, 'matchId' : ?1 }")
    List<Book> getUsersBookForMatch(String userId, String matchId);

    List<Book> findByUserId(String userId);

}
