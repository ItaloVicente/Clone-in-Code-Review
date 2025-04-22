
package com.couchbase.client.core.cluster;

import com.couchbase.client.core.endpoint.kv.KeyValueEndpoint;
import com.couchbase.client.core.endpoint.kv.KeyValueHandler;
import com.couchbase.client.core.env.CoreEnvironment;
import com.couchbase.client.core.env.DefaultCoreEnvironment;
import com.couchbase.client.core.message.config.FlushRequest;
import com.couchbase.client.core.message.internal.SignalFlush;
import com.couchbase.client.core.message.kv.GetRequest;
import com.couchbase.client.core.message.kv.GetResponse;
import com.couchbase.client.core.tracing.SlowOperationTracer;
import com.couchbase.client.core.util.ClusterDependentTest;
import com.couchbase.client.core.util.TestProperties;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class ServerTracingTest {

    static CoreEnvironment env;
    KeyValueEndpoint endpoint;

    @BeforeClass
    public static void init() throws Exception {
        env = DefaultCoreEnvironment
            .builder()
            .tracer(new SlowOperationTracer())
            .build();
    }

    @Before
    public void setup() {
        endpoint = new KeyValueEndpoint(
            TestProperties.seedNode(),
            TestProperties.bucket(),
            TestProperties.adminUser(),
            TestProperties.adminPassword(),
            11210,
            env,
            null
        );
        endpoint.connect().timeout(10, TimeUnit.SECONDS).toBlocking().single();
    }

    @After
    public void teardown() {
        endpoint.disconnect().toBlocking().single();
    }

    @AfterClass
    public static void shutdown() {
        env.shutdown(2, TimeUnit.SECONDS);
    }

    @Test
    public void shouldDecodeGetWithTrace() throws Exception {

        GetRequest request = new GetRequest("key", TestProperties.bucket());
        request.partition((short) 656);
        endpoint.send(request);
        endpoint.send(SignalFlush.INSTANCE);
        GetResponse response = (GetResponse) request.observable().toBlocking().single();
        System.err.println(response);
        System.err.println(response.serverDuration());
    }


}
