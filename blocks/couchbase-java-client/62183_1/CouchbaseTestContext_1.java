package com.couchbase.client.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

import com.couchbase.client.core.CouchbaseException;
import com.couchbase.client.core.logging.CouchbaseLogger;
import com.couchbase.client.core.logging.CouchbaseLoggerFactory;
import com.couchbase.client.java.bucket.BucketType;
import com.couchbase.client.java.cluster.ClusterManager;
import com.couchbase.client.java.cluster.DefaultBucketSettings;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import com.couchbase.client.java.error.InvalidPasswordException;
import com.couchbase.client.java.util.CouchbaseTestContext;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class CredentialsAuthenticationTest  {

    private static final CouchbaseLogger LOGGER = CouchbaseLoggerFactory.getInstance(CredentialsAuthenticationTest.class);

    private static CouchbaseTestContext ctx;

    @BeforeClass
    public static void connect() throws Exception {
        ctx = CouchbaseTestContext.builder()
                .withEnv(DefaultCouchbaseEnvironment.builder().connectTimeout(10000))
                .bucketName("creds")
                .bucketPassword("protected")
                .adhoc(true)
                .bucketQuota(100)
                .bucketReplicas(1)
                .bucketType(BucketType.COUCHBASE)
                .build();
    }

    @AfterClass
    public static void disconnect() throws InterruptedException {
        ctx.destroyBucketAndDisconnect();
    }

    @Test
    public void testClusterManagerCredentialsInitialized() {
        String[][] creds = ctx.cluster().credentialsManager().getCredentials(AuthenticationContext.CLUSTER_MANAGEMENT, null);
        assertNotNull(creds);
        assertNotNull(creds[0]);
        assertEquals(ctx.adminName(), creds[0][0]);
        assertEquals(ctx.adminPassword(), creds[0][1]);
    }

    @Test
    public void testClusterManagerAutoOpening() {
        ClusterManager manager = ctx.cluster().clusterManager();

        assertNotSame(ctx.clusterManager(), manager);
        assertEquals(true, manager.hasBucket(ctx.bucketName()));
    }

    @Test
    public void testBucketOpeningWithoutExplicitPassword() {
        String name = "testBucketOpeningWithoutExplicitPassword";
        ctx.clusterManager().insertBucket(
                DefaultBucketSettings.builder()
                    .name(name)
                    .password("protected")
                    .quota(100)
                    .build());

        try {
            try {
                ctx.cluster().openBucket(name);
            } catch (CouchbaseException e) {
                LOGGER.warn("", e);
            }

            ctx.cluster().credentialsManager().addBucketCredential(name, "protected");

            Bucket bucket = ctx.cluster().openBucket(name);
            bucket.upsert(JsonDocument.create("toto"));
            assertNotNull(bucket);
        } finally {
            ctx.clusterManager().removeBucket(name);
        }
    }
}
