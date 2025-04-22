package com.couchbase.client.java.query;

import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.document.json.JsonValue;

public abstract class AbstractQuery implements Query {

    protected abstract String statementType();

    protected abstract Object statementValue();

    protected abstract JsonValue statementParameters();

    @Override
    public String toN1QL() {
        JsonObject query = JsonObject.create().put(statementType(), statementValue());
        populateParameters(query, statementParameters());
        return query.toString(); //return json-escaped string
    }

    public static void populateParameters(JsonObject query, JsonValue params) {
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
    }

}
