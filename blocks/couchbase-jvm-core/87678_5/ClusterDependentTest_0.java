
package com.couchbase.client.core.cluster;

import com.couchbase.client.core.message.kv.GetRequest;
import com.couchbase.client.core.message.kv.GetResponse;
import com.couchbase.client.core.util.ClusterDependentTest;
import io.opentracing.mock.MockSpan;
import io.opentracing.mock.MockTracer;
import org.junit.BeforeClass;
import org.junit.Test;

public class TracingTest extends ClusterDependentTest {

    @BeforeClass
    public static void setup() throws Exception {
        tracer = new MockTracer();
        connect();
    }

    @Test
    public void shouldCaptureBasicTracingInfos() {
        MockTracer mt = (MockTracer) env().tracer();

        GetRequest request = new GetRequest("key", bucket());
        request.span(tracer.buildSpan("GetRequest").startActive(true).span());

        GetResponse response = cluster().<GetResponse>send(request).toBlocking().single();
        request.span().finish();

        for (MockSpan span : mt.finishedSpans()) {
            System.err.println(span);
            System.err.println(span.tags());
            System.err.println(span.startMicros());
            System.err.println(span.finishMicros());
            System.err.println(span.finishMicros() - span.startMicros());
        }
    }
}
