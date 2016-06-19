package com.tai.bookmaker.web.rest.dto;

import java.util.List;

/**
 * Created by mf57 on 19.06.2016.
 */
public class UserBookInfoDTO {
    private List<BookDTO> wonBooks;
    private List<BookDTO> lostBooks;
    private List<BookDTO> pendingBooks;

    public UserBookInfoDTO(List<BookDTO> wonBooks, List<BookDTO> lostBooks, List<BookDTO> pendingBooks) {
        this.wonBooks = wonBooks;
        this.lostBooks = lostBooks;
        this.pendingBooks = pendingBooks;
    }

    public List<BookDTO> getWonBooks() {
        return wonBooks;
    }

    public List<BookDTO> getLostBooks() {
        return lostBooks;
    }

    public List<BookDTO> getPendingBooks() {
        return pendingBooks;
    }
}
