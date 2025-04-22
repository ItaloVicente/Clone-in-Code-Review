
package com.couchbase.client.java.query;

import static com.couchbase.client.java.query.Select.select;
import static org.junit.Assert.*;

import com.couchbase.client.java.SerializationHelper;
import com.couchbase.client.java.document.json.JsonObject;
import org.junit.Test;

public class StatementSerializationTest {

    @Test
    public void rawStatementShouldBeSerializable() throws Exception {
        Query.RawStatement st = new Query.RawStatement("test");

        byte[] bytes = SerializationHelper.serializeToBytes(st);
        assertNotNull(bytes);

        Query.RawStatement deserialized = SerializationHelper.deserializeFromBytes(bytes,
                Query.RawStatement.class);
        assertEquals(st.toString(), deserialized.toString());
    }
    @Test
    public void prepareStatementShouldBeSerializable() throws Exception {
        Statement toPrepare = select("*");
        PrepareStatement st = PrepareStatement.prepare(toPrepare);

        byte[] bytes = SerializationHelper.serializeToBytes(st);
        assertNotNull(bytes);

        PrepareStatement deserialized = SerializationHelper.deserializeFromBytes(bytes,
                PrepareStatement.class);
        assertEquals(st.toString(), deserialized.toString());
        assertEquals(PrepareStatement.PREPARE_PREFIX + toPrepare.toString(), deserialized.toString());
    }
    @Test
    public void queryPlanShouldBeSerializable() throws Exception {
        JsonObject internalPlan = JsonObject.create().put("plan", "test");
        QueryPlan plan = new QueryPlan(internalPlan);

        byte[] bytes = SerializationHelper.serializeToBytes(plan);
        assertNotNull(bytes);

        QueryPlan deserialized = SerializationHelper.deserializeFromBytes(bytes,
                QueryPlan.class);
        assertNotNull(deserialized);
        assertNotNull(deserialized.plan());
        assertEquals(plan.plan(), deserialized.plan());
        assertEquals(plan.toString(), deserialized.toString());
    }



}
