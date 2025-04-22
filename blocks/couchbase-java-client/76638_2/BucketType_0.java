package com.couchbase.client.java;

import com.couchbase.client.java.auth.PasswordAuthenticator;
import com.couchbase.client.java.bucket.BucketType;
import com.couchbase.client.java.cluster.BucketSettings;
import com.couchbase.client.java.cluster.ClusterManager;
import com.couchbase.client.java.cluster.DefaultBucketSettings;
import com.couchbase.client.java.util.TestProperties;
import com.couchbase.client.java.util.features.Version;
import org.junit.AfterClass;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import rx.Observable;
import rx.functions.Func1;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EphemeralBucketTest {

    private static CouchbaseCluster couchbaseCluster;
    private static ClusterManager clusterManager;
    private static final Set<String> BUCKETS = new HashSet<String>(2);

    static {
        BUCKETS.add("insertEphBucket");
    }

    @BeforeClass
    public static void setup() {
        couchbaseCluster = CouchbaseCluster.create(TestProperties.seedNode());
        couchbaseCluster.authenticate(new PasswordAuthenticator(TestProperties.adminName(), TestProperties.adminPassword()));
        clusterManager = couchbaseCluster.clusterManager();

        Version minimumVersion = Version.parseVersion("5.0.0");
        Assume.assumeTrue(
            "Cluster is under " + minimumVersion,
            clusterManager.info().getMinVersion().compareTo(minimumVersion) >= 0
        );
    }

    @AfterClass
    public static void cleanup() {
        couchbaseCluster.disconnect();
    }

    @Before
    public void clearBuckets() {
        Observable.from(BUCKETS)
            .flatMap(new Func1<String, Observable<?>>() {
                @Override
                public Observable<?> call(String bucket) {
                    return clusterManager.async().removeBucket(bucket);
                }
            }).toBlocking().lastOrDefault(null);
    }

    @Test
    public void shouldManageEphemeralBucket() {
        BucketSettings settings = DefaultBucketSettings
            .builder()
            .type(BucketType.EPHEMERAL)
            .name("insertEphBucket")
            .password("password")
            .quota(128)
            .build();

        clusterManager.insertBucket(settings);

        List<BucketSettings> buckets = clusterManager.getBuckets();
        boolean found = false;
        for (BucketSettings bucket : buckets) {
            if (bucket.name().equals("insertEphBucket")) {
                found = true;
                assertEquals(BucketType.EPHEMERAL, bucket.type());
                assertEquals(128, bucket.quota());
            }
        }
        assertTrue(found);
        assertTrue(clusterManager.removeBucket("insertEphBucket"));
        assertFalse(clusterManager.hasBucket("insertEphBucket"));
    }
}
