
package com.couchbase.client.java;

import static org.junit.Assert.assertTrue;

import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.core.message.kv.subdoc.multi.Mutation;
import com.couchbase.client.java.bucket.BucketType;
import com.couchbase.client.java.subdoc.DocumentFragment;
import com.couchbase.client.java.subdoc.SubdocOptionsBuilder;
import com.couchbase.client.java.util.CouchbaseTestContext;
import com.couchbase.client.java.util.features.Version;
import org.junit.BeforeClass;
import org.junit.Test;

public class SubDocumentDocumentFlagsTests {

    private static CouchbaseTestContext ctx;

    @BeforeClass
    public static void connect() throws Exception {
        ctx = CouchbaseTestContext.builder()
                .bucketQuota(100)
                .bucketReplicas(1)
                .bucketType(BucketType.COUCHBASE)
                .build();

        ctx.ignoreIfClusterUnder(Version.parseVersion("5.0.0"));
    }

    public void removeIfExists(String key) {
        try {
            ctx.bucket().remove(key);
        } catch (Exception ex) {
        }
    }

    @Test
    public void createDocumentInSubdocMutateDictUpsert() {
        String key = "createDocumentInSubdocMutateDictInsert";
        removeIfExists(key);
        DocumentFragment<Mutation> response = ctx.bucket().mutateIn(key)
                .upsert("somepath.hello", "world", SubdocOptionsBuilder.builder().createParents(true))
                .withExpiry(1)
                .createDocument(true)
                .execute();
        assertTrue(response.status(0) == ResponseStatus.SUCCESS);
        removeIfExists(key);
    }

    @Test
    public void createDocumentInSubdocMutateDictInsert() {
        String key = "createDocumentInSubdocMutateDictInsert";
        removeIfExists(key);
        DocumentFragment<Mutation> response = ctx.bucket().mutateIn(key)
                .insert("hello", "world")
                .createDocument(true)
                .execute();
        assertTrue(response.status(0) == ResponseStatus.SUCCESS);
        ctx.bucket().remove(key);
    }

    @Test
    public void createDocumentInSubdocMutateArrayPrepend() {
        String key = "createDocumentInSubdocMutateArrayPrepend";
        removeIfExists(key);
        DocumentFragment<Mutation> response = ctx.bucket().mutateIn(key)
                .arrayPrepend("hello", "world")
                .createDocument(true)
                .execute();
        assertTrue(response.status(0) == ResponseStatus.SUCCESS);
        ctx.bucket().remove(key);
    }

    @Test
    public void createDocumentInSubdocMutateArrayAppend() {
        String key = "createDocumentInSubdocMutateArrayAppend";
        removeIfExists(key);
        DocumentFragment<Mutation> response = ctx.bucket().mutateIn(key)
                .arrayAppend("hello", "world")
                .createDocument(true)
                .execute();
        assertTrue(response.status(0) == ResponseStatus.SUCCESS);
        ctx.bucket().remove(key);
    }

    @Test
    public void createDocumentInSubdocMutateArrayAddUnique() {
        String key = "createDocumentInSubdocMutateArrayAddUnique";
        removeIfExists(key);
        DocumentFragment<Mutation> response = ctx.bucket().mutateIn(key)
                .arrayAddUnique("hello", "world")
                .createDocument(true)
                .execute();
        assertTrue(response.status(0) == ResponseStatus.SUCCESS);
        ctx.bucket().remove(key);
    }

    @Test
    public void createDocumentInSubdocCounter() {
        String key = "createDocumentInSubdocCounter";
        removeIfExists(key);
        DocumentFragment<Mutation> response = ctx.bucket().mutateIn(key)
                .counter("counter", 1)
                .createDocument(true)
                .execute();
        assertTrue(response.status(0) == ResponseStatus.SUCCESS);
        ctx.bucket().remove(key);
    }
}
