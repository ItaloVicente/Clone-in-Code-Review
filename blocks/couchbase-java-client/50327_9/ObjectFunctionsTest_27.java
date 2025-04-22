
package com.couchbase.client.java.query.dsl.functions;

import static com.couchbase.client.java.query.dsl.Expression.x;
import static com.couchbase.client.java.query.dsl.functions.ObjectFunctions.objectLength;
import static com.couchbase.client.java.query.dsl.functions.ObjectFunctions.objectNames;
import static com.couchbase.client.java.query.dsl.functions.ObjectFunctions.objectPairs;
import static com.couchbase.client.java.query.dsl.functions.ObjectFunctions.objectValues;
import static org.junit.Assert.*;

import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.dsl.Expression;
import org.junit.Test;

public class ObjectFunctionsTest {

    @Test
    public void testObjectLength() throws Exception {
        Expression e1 = objectLength(x(1));
        Expression e2 = objectLength("1");
        Expression e3 = objectLength(JsonObject.create());

        assertEquals(e1.toString(), e2.toString());
        assertEquals("OBJECT_LENGTH(1)", e1.toString());
        assertEquals("OBJECT_LENGTH({})", e3.toString());
    }

    @Test
    public void testObjectNames() throws Exception {
        Expression e1 = objectNames(x(1));
        Expression e2 = objectNames("1");
        Expression e3 = objectNames(JsonObject.empty());

        assertEquals(e1.toString(), e2.toString());
        assertEquals("OBJECT_NAMES(1)", e1.toString());
        assertEquals("OBJECT_NAMES({})", e3.toString());
    }

    @Test
    public void testObjectPairs() throws Exception {
        Expression e1 = objectPairs(x(1));
        Expression e2 = objectPairs("1");
        Expression e3 = objectPairs(JsonObject.empty());

        assertEquals(e1.toString(), e2.toString());
        assertEquals("OBJECT_PAIRS(1)", e1.toString());
        assertEquals("OBJECT_PAIRS({})", e3.toString());
    }

    @Test
    public void testObjectValues() throws Exception {
        Expression e1 = objectValues(x(1));
        Expression e2 = objectValues("1");
        Expression e3 = objectValues(JsonObject.empty());

        assertEquals(e1.toString(), e2.toString());
        assertEquals("OBJECT_VALUES(1)", e1.toString());
        assertEquals("OBJECT_VALUES({})", e3.toString());
    }
}
