
package com.couchbase.client.java.query.dsl.functions;

import static com.couchbase.client.java.query.dsl.Expression.x;
import static com.couchbase.client.java.query.dsl.functions.MetaFunctions.base64;
import static com.couchbase.client.java.query.dsl.functions.MetaFunctions.meta;
import static com.couchbase.client.java.query.dsl.functions.MetaFunctions.uuid;
import static org.junit.Assert.*;

import com.couchbase.client.java.query.dsl.Expression;
import org.junit.Test;

public class MetaFunctionsTest {

    @Test
    public void testMeta() throws Exception {
        Expression e1 = meta(x(1));
        Expression e2 = meta("1");

        assertEquals(e1.toString(), e2.toString());
        assertEquals("META(1)", e1.toString());
    }

    @Test
    public void testBase64() throws Exception {
        Expression e1 = base64(x(1));
        Expression e2 = base64("1" );

        assertEquals(e1.toString(), e2.toString());
        assertEquals("BASE64(1)", e1.toString());
    }

    @Test
    public void testUuid() throws Exception {
        assertEquals("UUID()", uuid().toString());
    }
}
