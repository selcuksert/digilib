package com.corp.libapp.search.service;

import com.corp.libapp.search.event.BookAdded;
import com.corp.libapp.search.event.BookDeleted;
import com.corp.libapp.search.model.Book;
import com.corp.libapp.search.model.UrlName;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BookService {
    private static final Logger LOG = LoggerFactory.getLogger(BookService.class);
    private final ApplicationEventPublisher events;
    private final QueryBookService queryBookService;

    @Transactional
    public void addBook(String isbn) {
        try {
            LOG.debug("Adding Book: {}", isbn);
            Map<String, Book> bookQueryResponse = queryBookService.queryBookWithISBN(isbn).getBody();
            assert bookQueryResponse != null;
            bookQueryResponse.forEach((isbnResponse, book) -> {
                List<UrlName> authors = book.authors();
                List<String> authorList = new ArrayList<>();
                authors.forEach(author -> authorList.add(author.name()));
                BookAdded bookAdded = new BookAdded(isbnResponse, book.title(), book.url(), authorList);
                events.publishEvent(bookAdded);
            });
        } catch (Exception e) {
            LOG.error("Error:", e);
        }
    }

    @Transactional
    public void deleteBook(String isbn) {
        try {
            LOG.debug("Deleting Book: {}", isbn);
            BookDeleted bookDeleted = new BookDeleted(isbn);
            events.publishEvent(bookDeleted);
        } catch (Exception e) {
            LOG.error("Error:", e);
        }
    }
}
