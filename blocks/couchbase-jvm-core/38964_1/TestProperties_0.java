package com.couchbase.client.core;

import com.couchbase.client.core.config.ConfigurationException;
import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.core.message.cluster.OpenBucketRequest;
import com.couchbase.client.core.message.cluster.OpenBucketResponse;
import com.couchbase.client.core.message.cluster.SeedNodesRequest;
import com.couchbase.client.core.util.TestProperties;
import org.junit.Test;
import rx.Observable;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class BucketLifecycleTest {

    @Test
    public void shouldSuccessfullyOpenBucket() {
        CouchbaseCore core = new CouchbaseCore();

        core.send(new SeedNodesRequest(Arrays.asList(TestProperties.seedNode())));
        OpenBucketRequest request = new OpenBucketRequest(TestProperties.bucket(), TestProperties.password());
        Observable<OpenBucketResponse> response = core.send(request);
        assertEquals(ResponseStatus.SUCCESS, response.toBlocking().single().status());
    }

    @Test(expected = ConfigurationException.class)
    public void shouldFailWithNoSeedNodeList() {
        OpenBucketRequest request = new OpenBucketRequest(TestProperties.bucket(), TestProperties.password());
        new CouchbaseCore().send(request).toBlocking().single();
    }

    @Test(expected = ConfigurationException.class)
    public void shouldFailWithEmptySeedNodeList() {
        CouchbaseCore core = new CouchbaseCore();
        core.send(new SeedNodesRequest(Collections.<String>emptyList()));
        OpenBucketRequest request = new OpenBucketRequest(TestProperties.bucket(), TestProperties.password());
        core.send(request).toBlocking().single();
    }

    @Test(expected = ConfigurationException.class)
    public void shouldFailOpeningNonExistentBucket() {
        CouchbaseCore core = new CouchbaseCore();

        core.send(new SeedNodesRequest(Arrays.asList(TestProperties.seedNode())));
        OpenBucketRequest request = new OpenBucketRequest(TestProperties.bucket() + "asd", TestProperties.password());
        core.send(request).toBlocking().single();
    }

    @Test(expected = ConfigurationException.class)
    public void shouldFailOpeningBucketWithWrongPassword() {
        CouchbaseCore core = new CouchbaseCore();

        core.send(new SeedNodesRequest(Arrays.asList(TestProperties.seedNode())));
        OpenBucketRequest request = new OpenBucketRequest(TestProperties.bucket(), TestProperties.password() + "asd");
        core.send(request).toBlocking().single();
    }

    @Test(expected = ConfigurationException.class)
    public void shouldFailOpeningWithWrongHost() {
        CouchbaseCore core = new CouchbaseCore();

        core.send(new SeedNodesRequest(Arrays.asList("certainlyInvalidHostname")));
        OpenBucketRequest request = new OpenBucketRequest(TestProperties.bucket(), TestProperties.password() + "asd");
        core.send(request).toBlocking().single();
    }

}
