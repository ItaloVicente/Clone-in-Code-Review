package com.couchbase.client.java;

import com.couchbase.client.java.auth.PasswordAuthenticator;
import com.couchbase.client.java.cluster.ClusterManager;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.util.TestProperties;
import com.couchbase.client.java.util.features.Version;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;

public class CollectionTest {

    private static Cluster couchbaseCluster;
    private static Bucket bucket;
    private static ClusterManager clusterManager;

    @BeforeClass
    public static void setup() {
        couchbaseCluster = CouchbaseCluster.create(TestProperties.seedNode());
        couchbaseCluster.authenticate(new PasswordAuthenticator(TestProperties.adminName(), TestProperties.adminPassword()));

        clusterManager = couchbaseCluster.clusterManager();
        Version minimumVersion = Version.parseVersion("6.5.0");
        Assume.assumeTrue(
                "Cluster is under " + minimumVersion,
                clusterManager.info().getMinVersion().compareTo(minimumVersion) >= 0
        );
        System.setProperty("com.couchbase.enableCollections", "true");
        bucket = couchbaseCluster.openBucket(TestProperties.bucket());
    }

    @AfterClass
    public static void cleanup() {
        couchbaseCluster.disconnect();
    }

    @Test
    public void testDefaultCollection() {
        Collection c = bucket.scope("defaut").collection("default", 0);
        JsonDocument document = c.upsert(JsonDocument.create("foo", JsonObject.create().put("bar", "baz")));
        Assert.assertTrue(document.cas() != 0);
    }

}
