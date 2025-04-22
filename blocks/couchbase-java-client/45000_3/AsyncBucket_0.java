package com.couchbase.client.java;

import static com.couchbase.client.java.query.Select.select;
import static com.couchbase.client.java.query.dsl.Expression.x;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.PrepareStatement;
import com.couchbase.client.java.query.PreparedQuery;
import com.couchbase.client.java.query.QueryPlan;
import com.couchbase.client.java.query.QueryResult;
import com.couchbase.client.java.query.QueryRow;
import com.couchbase.client.java.query.Statement;
import com.couchbase.client.java.util.ClusterDependentTest;
import org.junit.BeforeClass;
import org.junit.Test;

public class QueryTest extends ClusterDependentTest {

    @BeforeClass
    public static void init() {
        bucket().insert(JsonDocument.create("test1", JsonObject.create().put("item", "value")));
        bucket().insert(JsonDocument.create("test2", JsonObject.create().put("item", 123)));
    }

    @Test
    public void shouldProduceAndExecutePlan() {
        Statement toPrepare = select(x("*")).from("default").where(x("item").eq(x("$1")));
        PrepareStatement prepare = PrepareStatement.prepare(toPrepare);

        QueryPlan plan = bucket().queryPrepare(prepare);

        assertNotNull(plan);
        assertTrue(plan.plan().containsKey("signature"));
        assertTrue(plan.plan().containsKey("operator"));
        assertFalse(plan.plan().getObject("operator").isEmpty());

        QueryResult response = bucket().query(new PreparedQuery(plan, JsonArray.from(123)));
        assertTrue(response.success());
        List<QueryRow> rows = response.allRows();
        assertEquals(1, rows.size());
        assertTrue(rows.get(0).value().toString().contains("123"));
    }
}
