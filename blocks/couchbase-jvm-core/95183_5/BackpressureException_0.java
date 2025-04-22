
package com.couchbase.client.core;

import com.couchbase.client.core.env.CoreEnvironment;
import com.couchbase.client.core.env.DefaultCoreEnvironment;
import com.couchbase.client.core.message.cluster.CloseBucketRequest;
import com.couchbase.client.core.message.cluster.OpenBucketRequest;
import com.couchbase.client.core.message.cluster.OpenBucketResponse;
import com.couchbase.client.core.message.cluster.SeedNodesRequest;
import com.couchbase.client.core.tracing.RingBufferMonitor;
import com.couchbase.client.core.util.ClusterDependentTest;
import com.couchbase.client.core.util.TestProperties;
import org.junit.AfterClass;
import org.junit.Test;
import rx.Observable;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class RingBufferMonitorTest {
    private static final CoreEnvironment ENV = DefaultCoreEnvironment.create();

    @AfterClass
    public static final void cleanup() {
        ENV.shutdown();
    }

    @Test
    public void afterResponseRingBufferMonitorShouldBeEmpty() throws Exception {
        CouchbaseCore core = new CouchbaseCore(ENV);
        RingBufferMonitor.instance().reset();

        core.send(new SeedNodesRequest(Arrays.asList(TestProperties.seedNode())));
        OpenBucketRequest request;
        if (ClusterDependentTest.minClusterVersion()[0] >= 5) {
            request = new OpenBucketRequest(TestProperties.bucket(), TestProperties.adminUser(), TestProperties.adminPassword());
        } else {
            request = new OpenBucketRequest(TestProperties.bucket(), TestProperties.username(), TestProperties.password());
        }
        core.send(request).toBlocking().single();
        BackpressureException exception = RingBufferMonitor.instance().createException();
        assertEquals(0, exception.diagostics().totalCount());
        core.send(new CloseBucketRequest(TestProperties.bucket())).toBlocking().single();
    }


}
