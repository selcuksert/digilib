package com.corp.libapp.search.event;

import org.jmolecules.event.types.DomainEvent;

import java.util.List;

public record BookAdded(String isbn, String title, String url, List<String> authors) implements DomainEvent {
}
