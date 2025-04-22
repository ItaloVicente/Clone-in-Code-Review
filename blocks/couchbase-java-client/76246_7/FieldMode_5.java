package com.couchbase.client.java.search.sort;


public enum FieldMissing {
    FIRST("first"),
    LAST("last");

    private final String value;

    FieldMissing(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
