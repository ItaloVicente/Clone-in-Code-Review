package com.couchbase.client.java.fts.result;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public interface SearchStatus {

    long totalCount();

    long successCount();

    long errorCount();

    boolean isSuccess();
}
