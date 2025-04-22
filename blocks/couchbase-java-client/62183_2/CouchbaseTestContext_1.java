package com.couchbase.client.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;

import com.couchbase.client.java.bucket.BucketType;
import com.couchbase.client.java.cluster.ClusterManager;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import com.couchbase.client.java.error.InvalidPasswordException;
import com.couchbase.client.java.util.CouchbaseTestContext;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CredentialsAuthenticationTest  {

    private static CouchbaseTestContext ctx;

    private static String key;
    private static JsonObject testJson;
    private static JsonDocument testDoc;


    private Bucket bucketToClean;
    private CredentialsManager spyCredentialsManager;

    @BeforeClass
    public static void connect() throws Exception {
        ctx = CouchbaseTestContext.builder()
                .withEnv(DefaultCouchbaseEnvironment.builder()
                        .useBucketCache(false)
                        .connectTimeout(10000))
                .bucketName("creds")
                .bucketPassword("protected")
                .adhoc(true)
                .bucketQuota(100)
                .bucketReplicas(1)
                .bucketType(BucketType.COUCHBASE)
                .build();
        ctx.bucket().close();

        key = "credentialAuthenticationTest";
        testJson = JsonObject.create().put("test", "credentials");
        testDoc = JsonDocument.create(key, testJson);
    }

    @AfterClass
    public static void disconnect() throws InterruptedException {
        ctx.destroyBucketAndDisconnect();
    }

    @Before
    public void prepareTest() {
        ctx.cluster().setCredentialsManager(new CredentialsManager());
    }

    @After
    public void cleanupTest() {
        if (bucketToClean != null) {
            bucketToClean.close();
        }
    }

    @Test
    public void testClusterManagerCredentialsUninitialized() {
        ctx.cluster().clusterManager(ctx.adminName(), ctx.adminPassword());
        bucketToClean = ctx.cluster().openBucket(ctx.bucketName(), ctx.bucketPassword());

        String[][] creds = ctx.cluster().credentialsManager().getCredentials(AuthenticationContext.CLUSTER_MANAGEMENT, null);
        assertNotNull(creds);
        assertEquals(1, creds.length);
        assertNotNull(creds[0]);
        assertEquals(2, creds[0].length);
        assertNull(creds[0][0]);
        assertNull(creds[0][1]);

        creds = ctx.cluster().credentialsManager().getCredentials(AuthenticationContext.BUCKET_KEYVALUE, ctx.bucketName());
        assertNotNull(creds);
        assertEquals(1, creds.length);
        assertNotNull(creds[0]);
        assertEquals(2, creds[0].length);
        assertEquals(ctx.bucketName(), creds[0][0]);
        assertNull(creds[0][1]);

        creds = ctx.cluster().credentialsManager().getCredentials(AuthenticationContext.CLUSTER_N1QL, null);
        assertNotNull(creds);
        assertEquals(0, creds.length);
    }

    @Test
    public void testClusterManagerAutoOpening() {
        ctx.cluster().credentialsManager().addClusterCredentials(ctx.adminName(), ctx.adminPassword());
        ClusterManager manager = ctx.cluster().clusterManager();

        assertNotSame(ctx.clusterManager(), manager);
        assertEquals(true, manager.hasBucket(ctx.bucketName()));
    }

    @Test(expected = InvalidPasswordException.class)
    public void testBucketOpeningWithoutExplicitPasswordFailsWhenNoCredentials() {
        bucketToClean = ctx.cluster().openBucket(ctx.bucketName());
    }

    @Test
    public void testBucketOpeningWithoutExplicitPasswordSucceedsWithCredentials() {
        ctx.cluster().credentialsManager().addBucketCredential(ctx.bucketName(), ctx.bucketPassword());
        bucketToClean = ctx.cluster().openBucket(ctx.bucketName());
        bucketToClean.upsert(testDoc);

        assertNotSame(ctx.bucket(), bucketToClean);
        assertEquals(testJson, bucketToClean.get(key).content());
    }

    @Test
    public void testDefaultBucketOpeningWithoutCredentialsTriesDefaultPassword() {
        bucketToClean = ctx.cluster().openBucket(CouchbaseAsyncCluster.DEFAULT_BUCKET);
        bucketToClean.upsert(testDoc);

        assertNotSame(ctx.bucket(), bucketToClean);
        assertEquals(testJson, bucketToClean.get(key).content());
    }
}
