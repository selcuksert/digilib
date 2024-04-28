package com.corp.libapp.search.event;

import org.jmolecules.event.types.DomainEvent;

public record BookDeleted(String isbn) implements DomainEvent {
}
