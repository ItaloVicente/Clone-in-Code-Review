package com.couchbase.client.java;

import com.couchbase.client.java.auth.PasswordAuthenticator;
import com.couchbase.client.java.cluster.ClusterManager;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.JsonLongDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.options.CounterOptions;
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

    @Test
    public void testReactiveCollections() {
        Scope scope = bucket.scope("defaut");
        ReactiveCollection two = scope.reactiveCollection("two", 2);
        ReactiveCollection three = scope.reactiveCollection("three", 3);
        ReactiveCollection four = scope.reactiveCollection("four", 4);
        ReactiveCollection five = scope.reactiveCollection("five", 5);
        ReactiveCollection six = scope.reactiveCollection("six", 6);
        ReactiveCollection seven = scope.reactiveCollection("seven", 7);
        ReactiveCollection eight = scope.reactiveCollection("eight", 8);
        ReactiveCollection nine = scope.reactiveCollection("nine", 9);

        if (two.exists("rfoo").toBlocking().single()) {
            two.remove(JsonDocument.create("rfoo", JsonObject.create().put("bar", "baz1"))).toBlocking().single();
        }
        two.insert(JsonDocument.create("rfoo", JsonObject.create().put("bar", "baz1"))).toBlocking().single();
        three.upsert(JsonDocument.create("rfoo", JsonObject.create().put("bar", "baz2"))).toBlocking().single();
        four.upsert(JsonDocument.create("rfoo", JsonObject.create().put("bar", "baz3"))).toBlocking().single();
        five.upsert(JsonDocument.create("rfoo", JsonObject.create().put("bar", "baz4"))).toBlocking().single();
        six.upsert(JsonDocument.create("rfoo", JsonObject.create().put("bar", "baz5"))).toBlocking().single();
        seven.upsert(JsonDocument.create("rfoo", JsonObject.create().put("bar", "baz6"))).toBlocking().single();
        eight.upsert(JsonDocument.create("rfoo", JsonObject.create().put("bar", "baz7"))).toBlocking().single();
        nine.upsert(JsonDocument.create("rfoo", JsonObject.create().put("bar", "baz8"))).toBlocking().single();

        JsonDocument d = two.get("rfoo", JsonDocument.class).toBlocking().single();
        Assert.assertTrue(d.content().toMap().equals(JsonObject.create().put("bar", "baz1").toMap()));
        d = three.get("rfoo", JsonDocument.class).toBlocking().single();
        Assert.assertTrue(d.content().toMap().equals(JsonObject.create().put("bar", "baz2").toMap()));
        d = four.get("rfoo", JsonDocument.class).toBlocking().single();
        Assert.assertTrue(d.content().toMap().equals(JsonObject.create().put("bar", "baz3").toMap()));
        d = five.get("rfoo", JsonDocument.class).toBlocking().single();
        Assert.assertTrue(d.content().toMap().equals(JsonObject.create().put("bar", "baz4").toMap()));
        d = six.get("rfoo", JsonDocument.class).toBlocking().single();
        Assert.assertTrue(d.content().toMap().equals(JsonObject.create().put("bar", "baz5").toMap()));
        d = seven.get("rfoo", JsonDocument.class).toBlocking().single();
        Assert.assertTrue(d.content().toMap().equals(JsonObject.create().put("bar", "baz6").toMap()));
        d = eight.get("rfoo", JsonDocument.class).toBlocking().single();
        Assert.assertTrue(d.content().toMap().equals(JsonObject.create().put("bar", "baz7").toMap()));
        d = nine.get("rfoo", JsonDocument.class).toBlocking().single();
        Assert.assertTrue(d.content().toMap().equals(JsonObject.create().put("bar", "baz8").toMap()));

        nine.remove("rcounter", JsonDocument.class).toBlocking().single();
        CounterOptions options = CounterOptions.newBuilder().initial(1).delta(1).expiry(2500).build();
        nine.counter("rcounter", options).toBlocking().single();
        nine.counter("rcounter", options).toBlocking().single();
        nine.counter("rcounter", options).toBlocking().single();
        JsonLongDocument document = nine.counter("rcounter", options).toBlocking().single();
        Assert.assertTrue(document.content() == 4L);
    }

    @Test
    public void testCollections() {
        Scope scope = bucket.scope("defaut");
        Collection two = scope.collection("two", 2);
        Collection three = scope.collection("three", 3);
        Collection four = scope.collection("four", 4);
        Collection five = scope.collection("five", 5);
        Collection six = scope.collection("six", 6);
        Collection seven = scope.collection("seven", 7);
        Collection eight = scope.collection("eight", 8);
        Collection nine = scope.collection("nine", 9);

        if (two.exists("foo")) {
            two.remove(JsonDocument.create("foo", JsonObject.create().put("bar", "baz1")));
        }
        two.insert(JsonDocument.create("foo", JsonObject.create().put("bar", "baz1")));
        three.upsert(JsonDocument.create("foo", JsonObject.create().put("bar", "baz2")));
        four.upsert(JsonDocument.create("foo", JsonObject.create().put("bar", "baz3")));
        five.upsert(JsonDocument.create("foo", JsonObject.create().put("bar", "baz4")));
        six.upsert(JsonDocument.create("foo", JsonObject.create().put("bar", "baz5")));
        seven.upsert(JsonDocument.create("foo", JsonObject.create().put("bar", "baz6")));
        eight.upsert(JsonDocument.create("foo", JsonObject.create().put("bar", "baz7")));
        nine.upsert(JsonDocument.create("foo", JsonObject.create().put("bar", "baz8")));

        JsonDocument d = two.get("foo", JsonDocument.class);
        Assert.assertTrue(d.content().toMap().equals(JsonObject.create().put("bar", "baz1").toMap()));
        d = three.get("foo", JsonDocument.class);
        Assert.assertTrue(d.content().toMap().equals(JsonObject.create().put("bar", "baz2").toMap()));
        d = four.get("foo", JsonDocument.class);
        Assert.assertTrue(d.content().toMap().equals(JsonObject.create().put("bar", "baz3").toMap()));
        d = five.get("foo", JsonDocument.class);
        Assert.assertTrue(d.content().toMap().equals(JsonObject.create().put("bar", "baz4").toMap()));
        d = six.get("foo", JsonDocument.class);
        Assert.assertTrue(d.content().toMap().equals(JsonObject.create().put("bar", "baz5").toMap()));
        d = seven.get("foo", JsonDocument.class);
        Assert.assertTrue(d.content().toMap().equals(JsonObject.create().put("bar", "baz6").toMap()));
        d = eight.get("foo", JsonDocument.class);
        Assert.assertTrue(d.content().toMap().equals(JsonObject.create().put("bar", "baz7").toMap()));
        d = nine.get("foo", JsonDocument.class);
        Assert.assertTrue(d.content().toMap().equals(JsonObject.create().put("bar", "baz8").toMap()));

        nine.remove("counter", JsonDocument.class);
        CounterOptions options = CounterOptions.newBuilder().initial(1).delta(1).expiry(2500).build();
        nine.counter("counter", options);
        nine.counter("counter", options);
        nine.counter("counter", options);
        JsonLongDocument document = nine.counter("counter", options);
        Assert.assertTrue(document.content() == 4L);
    }

}
