
package com.couchbase.client.java.query.dsl.functions;

import static com.couchbase.client.java.query.dsl.Expression.x;
import static com.couchbase.client.java.query.dsl.functions.JsonFunctions.decodeJson;
import static com.couchbase.client.java.query.dsl.functions.JsonFunctions.encodeJson;
import static com.couchbase.client.java.query.dsl.functions.JsonFunctions.encodedSize;
import static com.couchbase.client.java.query.dsl.functions.JsonFunctions.polyLength;
import static org.junit.Assert.*;

import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.dsl.Expression;
import org.junit.Test;

public class JsonFunctionsTest {

    @Test
    public void testDecodeJson() throws Exception {
        Expression e1 = decodeJson(x("jsonContainingField"));
        Expression e2 = decodeJson("{\"test\": true}");
        Expression e3 = decodeJson(JsonObject.create().put("test", true));

        assertEquals("DECODE_JSON(jsonContainingField)", e1.toString());
        assertEquals("DECODE_JSON(\"{\\\"test\\\":true}\")", e2.toString());
        assertEquals("DECODE_JSON(\"{\\\"test\\\":true}\")", e3.toString());
    }

    @Test
    public void testEncodeJson() throws Exception {
        Expression e1 = encodeJson(x(1));
        Expression e2 = encodeJson("1");

        assertEquals(e1.toString(), e2.toString());
        assertEquals("ENCODE_JSON(1)", e1.toString());
    }

    @Test
    public void testEncodedSize() throws Exception {
        Expression e1 = encodedSize(x(1));
        Expression e2 = encodedSize("1");

        assertEquals(e1.toString(), e2.toString());
        assertEquals("ENCODED_SIZE(1)", e1.toString());
    }

    @Test
    public void testPolyLength() throws Exception {
        Expression e1 = polyLength(x(1));
        Expression e2 = polyLength("1");

        assertEquals(e1.toString(), e2.toString());
        assertEquals("POLY_LENGTH(1)", e1.toString());
    }
}
