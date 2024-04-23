package com.corp.libapp.search.service;

import com.corp.libapp.search.event.BookAdded;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddBookService {
    private static final Logger LOG = LoggerFactory.getLogger(AddBookService.class);
    private final ApplicationEventPublisher events;

    public AddBookService(ApplicationEventPublisher events) {
        this.events = events;
    }

    @Transactional
    public void addBook(String isbn) {
        try {
            LOG.info("Adding Book: {}", isbn);
            BookAdded bookAdded = new BookAdded(isbn);
            events.publishEvent(bookAdded);
        } catch (Exception e) {
            LOG.error("Error:", e);
        }
    }
}
