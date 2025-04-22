package com.couchbase.client.java.fts.result;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public interface SearchMetrics {

    long took();

    long totalHits();

    double maxScore();
}
