package com.couchbase.client.java.fts.result;

public interface SearchMetrics {

    long took();
    long totalHits();
    double maxScore();
}
