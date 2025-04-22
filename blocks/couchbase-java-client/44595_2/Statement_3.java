package com.couchbase.client.java.query;

import com.couchbase.client.java.document.json.JsonObject;

public class SimpleQuery implements Query {

    private final Statement statement;

    public SimpleQuery(Statement statement) {
        this.statement = statement;
    }

    @Override
    public Statement statement() {
        return this.statement;
    }

    @Override
    public String toN1QL() {
        return JsonObject.create()
            .put("statement", this.statement.toString())
            .toString();
    }
}
