package com.couchbase.client.java.fts.result;

public interface SearchStatus {

    long totalCount();
    long successCount();
    long errorCount();
    boolean isSuccess();
}
