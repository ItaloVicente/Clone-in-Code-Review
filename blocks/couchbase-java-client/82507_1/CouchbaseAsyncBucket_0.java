package com.couchbase.client.java;

import com.couchbase.client.java.bucket.BucketType;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.JsonLongDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.error.CASMismatchException;
import com.couchbase.client.java.error.TemporaryFailureException;
import com.couchbase.client.java.error.TemporaryLockFailureException;
import com.couchbase.client.java.util.CouchbaseTestContext;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LockingTest {

    private static CouchbaseTestContext ctx;

    @BeforeClass
    public static void connect() throws Exception {
        ctx = CouchbaseTestContext.builder()
            .bucketQuota(256)
            .bucketType(BucketType.COUCHBASE)
            .flushOnInit(true)
            .enableFlush(true)
            .build();
    }

    @AfterClass
    public static void disconnect() throws InterruptedException {
        ctx.disconnect();
    }

    @Test(expected = CASMismatchException.class)
    public void shouldHandleLockOnUpsert() {
        String id = createAndLockDoc();
        ctx.bucket().upsert(JsonDocument.create(id, JsonObject.empty()));
    }

    @Test(expected = CASMismatchException.class)
    public void shouldHandleLockOnReplace() {
        String id = createAndLockDoc();
        ctx.bucket().replace(JsonDocument.create(id, JsonObject.empty()));
    }

    @Test(expected = CASMismatchException.class)
    public void shouldHandleLockOnRemove() {
        String id = createAndLockDoc();
        ctx.bucket().replace(JsonDocument.create(id, JsonObject.empty()));
    }

    @Test(expected = TemporaryFailureException.class)
    public void shouldHandleLockOnGetAndTouch() {
        String id = createAndLockDoc();
        ctx.bucket().getAndTouch(id, 10);
    }

    @Test(expected = TemporaryLockFailureException.class)
    public void shouldHandleLockOnGetAndLock() {
        String id = createAndLockDoc();
        ctx.bucket().getAndLock(id, 10);
    }

    @Test(expected = TemporaryFailureException.class)
    public void shouldHandleLockOnTouch() {
        String id = createAndLockDoc();
        ctx.bucket().touch(id, 10);
    }

    @Test(expected = TemporaryFailureException.class)
    public void shouldHandleLockOnAppend() {
        String id = createAndLockDoc();
        ctx.bucket().append(JsonDocument.create(id, JsonObject.empty()));
    }

    @Test(expected = TemporaryFailureException.class)
    public void shouldHandleLockOnPrepend() {
        String id = createAndLockDoc();
        ctx.bucket().prepend(JsonDocument.create(id, JsonObject.empty()));
    }

    @Test(expected = TemporaryLockFailureException.class)
    public void shouldHandleLockOnUnlock() {
        String id = createAndLockDoc();
        ctx.bucket().unlock(id, -1);
    }

    @Test(expected = TemporaryFailureException.class)
    public void shouldHandleLockOnCounter() {
        String id = UUID.randomUUID().toString();
        assertEquals(id, ctx.bucket().counter(id, 10, 0).id());
        assertTrue(ctx.bucket().getAndLock(id, 30, JsonLongDocument.class).cas() != 0);
        ctx.bucket().counter(id, 10);
    }

    @Test(expected = TemporaryFailureException.class)
    public void shouldHandleLockOnMutateIn() {
        String id = createAndLockDoc();
        ctx.bucket().mutateIn(id).upsert("foo", "bar").execute();
    }

    private String createAndLockDoc() {
        String id = UUID.randomUUID().toString();
        assertEquals(id, ctx.bucket().upsert(JsonDocument.create(id, JsonObject.empty())).id());
        assertTrue(ctx.bucket().getAndLock(id, 30).cas() != 0);
        return id;
    }
}
