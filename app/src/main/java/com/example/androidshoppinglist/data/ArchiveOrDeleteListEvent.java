package com.example.androidshoppinglist.data;

/**
 * Created by joanna on 25.08.16.
 */
public class ArchiveOrDeleteListEvent {

    private final long listCreatedAtTime;
    private final EventStatus status;

    public ArchiveOrDeleteListEvent(long listCreatedAtTime, EventStatus status) {
        this.listCreatedAtTime = listCreatedAtTime;
        this.status = status;
    }

    public long getListCreatedAtTime() {
        return listCreatedAtTime;
    }

    public EventStatus getStatus() {
        return status;
    }

    public enum EventStatus {
        DELETE,
        ARCHIVE
    }
}
