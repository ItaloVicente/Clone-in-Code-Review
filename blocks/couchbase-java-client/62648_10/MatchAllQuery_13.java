package com.couchbase.client.java.fts.queries;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.management.Query;

import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.fts.SearchParams;
import com.couchbase.client.java.fts.SearchQuery;

public class DocIdQuery extends SearchQuery {

    private List<String> docIds = new LinkedList<String>();

    public DocIdQuery(SearchParams searchParams, String... docIds) {
        super(searchParams);
        Collections.addAll(this.docIds, docIds);
    }

    public DocIdQuery docIds(String... docIds) {
        Collections.addAll(this.docIds, docIds);
        return this;
    }

    @Override
    public DocIdQuery boost(double boost) {
        super.boost(boost);
        return this;
    }

    @Override
    protected void injectParams(JsonObject input) {
        if (docIds.isEmpty()) {
            throw new IllegalArgumentException("DocID query needs at least one document ID");
        }

        JsonArray ids = JsonArray.from(docIds);
        input.put("ids", ids);
    }
}
