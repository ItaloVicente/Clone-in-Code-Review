package com.couchbase.client.java.query;

import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;

public class PreparedQuery extends ParametrizedQuery {

    public PreparedQuery(Statement statement, JsonArray positionalParams) {
        super(statement, positionalParams);
    }

    public PreparedQuery(Statement statement, JsonObject namedParams) {
       super(statement, namedParams);
    }

    @Override
    protected String statementType() {
        return "prepared";
    }
}
