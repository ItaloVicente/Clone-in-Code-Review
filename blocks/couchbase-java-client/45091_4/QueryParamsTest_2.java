package com.couchbase.client.java.query.consistency;

public enum ScanConsistency {

    NOT_BOUNDED,
    REQUEST_PLUS,
    STATEMENT_PLUS;

    public String n1ql() {
        return this.name().toLowerCase();
    }
}
