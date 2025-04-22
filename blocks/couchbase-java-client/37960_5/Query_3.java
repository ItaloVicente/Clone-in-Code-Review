package com.couchbase.client.java.query;

public enum OnError {

    STOP("stop"),
    CONTINUE("continue");

    private final String identifier;

    OnError(String identifier) {
        this.identifier = identifier;
    }

    public String identifier() {
        return identifier;
    }
}
