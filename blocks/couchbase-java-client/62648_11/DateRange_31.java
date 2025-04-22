package com.couchbase.client.java.fts.result;

public interface SearchStatus {

    long totalCount();
    long rowCount();
    long errorCount();
    boolean isSuccess();
}
