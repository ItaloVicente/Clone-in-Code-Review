package com.couchbase.client.java.search.sort;

public enum FieldType {
    AUTO("auto"),
    STRING("string"),
    NUMBER("number"),
    DATE("date");

    private final String value;

    FieldType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
