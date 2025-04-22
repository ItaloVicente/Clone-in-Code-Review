package com.couchbase.client.java.fts.result;

import com.couchbase.client.core.CouchbaseException;
import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.message.kv.ObserveRequest;
import com.couchbase.client.java.fts.result.facets.FacetResult;
import rx.Observable;
import rx.Observer;
import rx.exceptions.CompositeException;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public interface AsyncSearchQueryResult {

    SearchStatus status();

    Observable<SearchQueryRow> hits();

    Observable<FacetResult> facets();

    Observable<SearchMetrics> metrics();
}
