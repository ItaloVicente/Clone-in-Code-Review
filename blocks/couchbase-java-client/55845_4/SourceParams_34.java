
package com.couchbase.client.java.search;

import com.couchbase.client.java.document.json.JsonObject;

import java.util.Iterator;
import java.util.List;

public class SearchQueryResult implements Iterable<SearchQueryHit> {

    private final List<SearchQueryHit> hits;
    private final long took; // nanoseconds
    private final long totalHits;
    private final double maxScore;

    public SearchQueryResult(long took, long totalHits, double maxScore, List<SearchQueryHit> hits) {
        this.took = took;
        this.totalHits = totalHits;
        this.maxScore = maxScore;
        this.hits = hits;
    }

    public long took() {
        return took;
    }

    public long totalHits() {
        return totalHits;
    }

    public double maxScore() {
        return maxScore;
    }

    @Override
    public Iterator<SearchQueryHit> iterator() {
        return hits.iterator();
    }
}
