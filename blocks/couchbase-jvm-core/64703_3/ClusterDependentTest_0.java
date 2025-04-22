package com.couchbase.client.core.cluster;

import com.couchbase.client.core.event.CouchbaseEvent;
import com.couchbase.client.core.event.metrics.LatencyMetric;
import com.couchbase.client.core.event.metrics.NetworkLatencyMetricsEvent;
import com.couchbase.client.core.metrics.NetworkLatencyMetricsIdentifier;
import com.couchbase.client.core.util.ClusterDependentTest;
import org.junit.Test;
import rx.Observable;
import rx.functions.Func1;
import rx.observers.TestSubscriber;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class KeepAliveMessageTest extends ClusterDependentTest {

    @Test
    public void shouldSendKeepAlives() throws Exception {
        Observable<CouchbaseEvent> eventBus = env().eventBus().get();
        TestSubscriber<CouchbaseEvent> eventSubscriber = new TestSubscriber<CouchbaseEvent>();
        eventBus
                .filter(new Func1<CouchbaseEvent, Boolean>() {
                    @Override
                    public Boolean call(CouchbaseEvent event) {
                        return event instanceof NetworkLatencyMetricsEvent;
                    }
                }).subscribe(eventSubscriber);

        Thread.sleep(ClusterDependentTest.KEEPALIVE_INTERVAL + 1000);

        env().networkLatencyMetricsCollector().triggerEmit();

        Thread.sleep(100);

        List<CouchbaseEvent> events = eventSubscriber.getOnNextEvents();
        assertEquals(1, events.size());
        NetworkLatencyMetricsEvent event = (NetworkLatencyMetricsEvent) events.get(0);

        int keepalivesFound = 0;
        for (Map.Entry<NetworkLatencyMetricsIdentifier, LatencyMetric> metric : event.latencies().entrySet()) {
            System.err.println(metric.getKey());
            if (metric.getKey().request().equals("KeepAliveRequest")) {
                keepalivesFound++;
            }
        }
        assertTrue(keepalivesFound > 0);
    }
}
