package com.couchbase.client.java.fts.result.impl;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.fts.result.SearchMetrics;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class DefaultSearchMetrics implements SearchMetrics {

    private final long took;
    private final long totalHits;
    private final double maxScore;

    public DefaultSearchMetrics(long took, long totalHits, double maxScore) {
        this.took = took;
        this.totalHits = totalHits;
        this.maxScore = maxScore;
    }

    @Override
    public long took() {
        return this.took;
    }

    @Override
    public long totalHits() {
        return this.totalHits;
    }

    @Override
    public double maxScore() {
        return this.maxScore;
    }

    @Override
    public String toString() {
        return "DefaultSearchMetrics{" +
                "took=" + took +
                ", totalHits=" + totalHits +
                ", maxScore=" + maxScore +
                '}';
    }
}
