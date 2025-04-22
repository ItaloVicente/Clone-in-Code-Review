
package com.couchbase.client.core.cluster;

import com.couchbase.client.core.message.kv.GetRequest;
import com.couchbase.client.core.message.kv.GetResponse;
import com.couchbase.client.core.tracing.SlowOperationReporter;
import com.couchbase.client.core.tracing.SlowOperationTracer;
import com.couchbase.client.core.util.ClusterDependentTest;
import io.opentracing.Scope;
import io.opentracing.Span;
import org.junit.BeforeClass;
import org.junit.Test;

public class TracingTest extends ClusterDependentTest {

    @BeforeClass
    public static void setup() throws Exception {
        tracer = new SlowOperationTracer(SlowOperationReporter.createWithNoThresholds(10, 1000));
        connect();
    }

    @Test
    public void shouldCaptureBasicTracingInfos() throws Exception {
        SlowOperationTracer mt = (SlowOperationTracer) env().tracer();

        GetRequest request = new GetRequest("key", bucket());
        Scope scope = tracer.buildSpan("GetRequest").startActive(true);
        Span span = scope.span();
        request.span(span);

        GetResponse response = cluster().<GetResponse>send(request).toBlocking().single();
        request.span().finish();

            System.err.println(span);
            System.err.println(span.tags());
            System.err.println(span.startMicros());
            System.err.println(span.finishMicros());
            System.err.println(span.finishMicros() - span.startMicros());
        }*/

       Thread.sleep(100000);
    }
}
