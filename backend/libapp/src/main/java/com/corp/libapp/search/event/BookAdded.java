package com.corp.libapp.search.event;

import org.jmolecules.event.types.DomainEvent;

public record BookAdded(String isbn) implements DomainEvent {
}
