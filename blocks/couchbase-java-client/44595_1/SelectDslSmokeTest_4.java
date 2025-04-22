
package com.couchbase.client.java.query;

import static com.couchbase.client.java.query.Select.select;
import static com.couchbase.client.java.query.dsl.Expression.s;
import static com.couchbase.client.java.query.dsl.Expression.x;

import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import org.junit.Assert;
import org.junit.Test;

public class QueryToN1qlTest {

    @Test
    public void simpleQueryShouldJustProduceStatement() {
        SimpleQuery query = new SimpleQuery(select("*").from("tutorial").where(x("fname").eq(s("ian"))));

        Assert.assertEquals("{\"statement\":\"SELECT * FROM tutorial WHERE fname = \\\"ian\\\"\"}", query.toN1QL());
    }

    @Test
    public void parametrizedQueryWithArrayShouldProduceStatementAndArgs() {
        ParametrizedQuery query = new ParametrizedQuery(select("*"), JsonArray.from("aString", 123, true));

        JsonObject expected = JsonObject.create()
            .put("statement", "SELECT *")
            .put("args", JsonArray.from("aString", 123, true));

        Assert.assertEquals(expected.toString(), query.toN1QL());
    }

    @Test
    public void preparedQueryWithArrayShouldProducePreparedAndArgs() {
        PreparedQuery query = new PreparedQuery(select("*"), JsonArray.from("aString", 123, true));

        JsonObject expected = JsonObject.create()
            .put("prepared", "SELECT *")
            .put("args", JsonArray.from("aString", 123, true));

        Assert.assertEquals(expected.toString(), query.toN1QL());
    }

    @Test
    public void parametrizedQueryWithObjectShouldProduceStatementAndNamedParameters() {
        JsonObject args = JsonObject.create()
            .put("myParamString", "aString")
            .put("someInt", 123)
            .put("$fullN1qlParam", true);
        ParametrizedQuery query = new ParametrizedQuery(select("*"), args);

        JsonObject expected = JsonObject.create()
            .put("statement", "SELECT *")
            .put("$myParamString", "aString")
            .put("$someInt", 123)
            .put("$fullN1qlParam", true);

        Assert.assertEquals(expected.toString(), query.toN1QL());
    }

    @Test
    public void preparedQueryWithObjectShouldProducePreparedAndNamedParameters() {
        JsonObject args = JsonObject.create()
            .put("myParamString", "aString")
            .put("someInt", 123)
            .put("$fullN1qlParam", true);
        PreparedQuery query = new PreparedQuery(select("*"), args);

        JsonObject expected = JsonObject.create()
            .put("prepared", "SELECT *")
            .put("$myParamString", "aString")
            .put("$someInt", 123)
            .put("$fullN1qlParam", true);

        Assert.assertEquals(expected.toString(), query.toN1QL());
    }

}
