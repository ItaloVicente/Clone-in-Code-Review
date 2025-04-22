package com.couchbase.client.java;

import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.error.FlushDisabledException;
import com.couchbase.client.java.util.CouchbaseTestContext;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class FlushDisabledTest {

    private static CouchbaseTestContext ctx;

    @BeforeClass
    public static void init() throws InterruptedException {
        ctx = CouchbaseTestContext.builder()
            .bucketName("FlushDisabled")
            .enableFlush(false)
            .adhoc(true)
            .flushOnInit(false)
            .build();
    }

    @AfterClass
    public static void cleanup() {
        ctx.destroyBucketAndDisconnect();
    }

    @Test(expected = FlushDisabledException.class)
    public void shouldFailOnDisabledFlush() {
        ctx.bucketManager().flush();
    }
}
