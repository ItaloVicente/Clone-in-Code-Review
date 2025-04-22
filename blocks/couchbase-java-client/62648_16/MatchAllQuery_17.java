package com.couchbase.client.java.fts.queries;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;

public class DocIdQuery extends AbstractFtsQuery {

    private List<String> docIds = new LinkedList<String>();

    public DocIdQuery(String... docIds) {
        super();
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
