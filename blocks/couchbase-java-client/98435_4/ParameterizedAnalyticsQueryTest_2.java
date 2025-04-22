package com.couchbase.client.java.analytics;

import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import org.junit.Test;

import static org.junit.Assert.*;

public class ParameterizedAnalyticsQueryTest {

    @Test
    public void shouldPassNamedParams() {
        JsonObject named = JsonObject.create()
                .put("num", 1)
                .put("$b", "foobar");

        ParameterizedAnalyticsQuery query = new ParameterizedAnalyticsQuery(
                "select 1=num where a=$b",
                null,
                named,
                null
        );

        JsonObject expected = JsonObject.fromJson
                ("{\"$num\":1,\"statement\":\"select 1=num where a=$b\",\"$b\":\"foobar\"}");
        JsonObject result = query.query();
        assertEquals(result, expected);
    }

    @Test
    public void shouldPassPositionalParams() {
        JsonArray positional = JsonArray.from(1, "foo", true);

        ParameterizedAnalyticsQuery query = new ParameterizedAnalyticsQuery(
                "select 1=? where a=? or b=?",
                positional,
                null,
                null
        );

        JsonObject expected = JsonObject.fromJson
                ("{\"args\":[1,\"foo\",true],\"statement\":\"select 1=? where a=? or b=?\"}");
        JsonObject result = query.query();
        assertEquals(result, expected);
    }

}
