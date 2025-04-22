package com.couchbase.client.java.search.sort;

public enum FieldMode {
    DEFAULT("default"),
    MIN("min"),
    MAX("max");

    private final String value;

    FieldMode(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
