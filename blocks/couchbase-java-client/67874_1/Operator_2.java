package com.couchbase.client.java.query.core;

import static com.couchbase.client.java.query.core.Operator.EQUALS;

import java.util.Collections;
import java.util.Map;

import com.couchbase.client.java.document.json.JsonObject;

public class Example extends CriteriaBase {


    protected Example(JsonObject example, Map<String, Operator> operators) {
        super();
        for (String key: example.getNames()) {
            Operator operator = operators.get(key);
            if (operator == null) {
                operator = EQUALS;
            }

            addCriteria(key, operator, example.get(key));
        }
    }

    public static Example of(JsonObject example) {
        return new Example(example, Collections.<String, Operator>emptyMap());
    }

    public static Example of(JsonObject example, Map<String, Operator> operators) {
        return new Example(example, operators);
    }
}
