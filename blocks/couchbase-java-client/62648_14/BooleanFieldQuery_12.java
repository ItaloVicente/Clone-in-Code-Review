package com.couchbase.client.java.fts.queries;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.couchbase.client.java.fts.SearchParams;
import com.couchbase.client.java.fts.SearchQuery;

public abstract class AbstractCompoundQuery extends SearchQuery {

    List<SearchQuery> childQueries = new LinkedList<SearchQuery>();

    protected AbstractCompoundQuery(SearchQuery... queries) {
        super();
        addAll(queries);
    }

    protected void addAll(SearchQuery... queries) {
        Collections.addAll(childQueries, queries);
    }

    public List<SearchQuery> childQueries() {
        return this.childQueries; //TODO copy? immutable copy?
    }
}
