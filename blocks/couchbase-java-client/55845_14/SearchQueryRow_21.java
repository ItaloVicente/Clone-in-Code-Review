
package com.couchbase.client.java.search;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import java.util.Iterator;
import java.util.List;

@InterfaceAudience.Public
@InterfaceStability.Experimental
public class SearchQueryResult implements Iterable<SearchQueryRow> {

    private final List<SearchQueryRow> hits;
    private final long took; // nanoseconds
    private final long totalHits;
    private final double maxScore;

    public SearchQueryResult(long took, long totalHits, double maxScore, List<SearchQueryRow> hits) {
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

    public List<SearchQueryRow> hits() {
        return hits;
    }

    public double maxScore() {
        return maxScore;
    }

    @Override
    public Iterator<SearchQueryRow> iterator() {
        return hits.iterator();
    }
}
