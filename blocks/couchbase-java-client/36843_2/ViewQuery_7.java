package com.couchbase.client.java.view;

public enum Stale {

    TRUE("ok"),

    FALSE("false"),

    UPDATE_AFTER("update_after");

    private String identifier;

    private Stale(String identifier) {
        this.identifier = identifier;
    }

    public String identifier() {
        return identifier;
    }
}
