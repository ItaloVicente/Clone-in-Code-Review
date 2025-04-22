package com.couchbase.client.java.query;

import java.util.Map;

import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.document.json.JsonValue;

public class ParametrizedQuery implements Query {

    private final Statement statement;
    private final JsonValue params;

    public ParametrizedQuery(Statement statement, JsonArray positionalParams) {
        this.statement = statement;
        this.params = positionalParams;
    }

    public ParametrizedQuery(Statement statement, JsonObject namedParams) {
        this.statement = statement;
        this.params = namedParams;
    }

    protected String statementType() {
        return "statement";
    }

    @Override
    public Statement statement() {
        return this.statement;
    }

    @Override
    public String toN1QL() {
        JsonObject query = JsonObject.create()
                .put(statementType(), statement.toString());
        if (params instanceof JsonArray && !((JsonArray) params).isEmpty()) {
           query.put("args", (JsonArray) params);
        } else if (params instanceof JsonObject && !((JsonObject) params).isEmpty()) {
            JsonObject namedParams = (JsonObject) params;
            for (String key : namedParams.getNames()) {
                Object value = namedParams.get(key);
                if (key.charAt(0) != '$') {
                    query.put('$' + key, value);
                } else {
                    query.put(key, value);
                }
            }
        } //else do nothing, as if a simple statement
        return query.toString(); //return json-escaped string
    }

}
