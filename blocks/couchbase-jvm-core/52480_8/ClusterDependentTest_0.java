package com.couchbase.client.core.cluster;

import com.couchbase.client.core.event.CouchbaseEvent;
import com.couchbase.client.core.event.metric.AbstractLatencyMetricsEvent;
import com.couchbase.client.core.event.metric.CoreNetworkLatencyMetricsEvent;
import com.couchbase.client.core.message.kv.GetRequest;
import com.couchbase.client.core.message.kv.GetResponse;
import com.couchbase.client.core.message.kv.InsertRequest;
import com.couchbase.client.core.message.kv.InsertResponse;
import com.couchbase.client.core.message.kv.UpsertRequest;
import com.couchbase.client.core.message.kv.UpsertResponse;
import com.couchbase.client.core.util.ClusterDependentTest;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import org.junit.Test;
import rx.Observable;
import rx.functions.Func1;
import rx.observers.TestSubscriber;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NetworkMetricsTest extends ClusterDependentTest {

    @Test
    public void shouldCapturePerformedOperations() throws Exception {
        Observable<CouchbaseEvent> eventBus = env().eventBus().get();
        TestSubscriber<CouchbaseEvent> eventSubscriber = new TestSubscriber<CouchbaseEvent>();
        eventBus
            .filter(new Func1<CouchbaseEvent, Boolean>() {
            @Override
            public Boolean call(CouchbaseEvent event) {
                return event instanceof CoreNetworkLatencyMetricsEvent;
            }
        }).subscribe(eventSubscriber);

        assertTrue(eventSubscriber.getOnErrorEvents().isEmpty());
        assertTrue(eventSubscriber.getOnNextEvents().isEmpty());

        InsertResponse insertResponse = cluster()
            .<InsertResponse>send(new InsertRequest("perfIns", Unpooled.copiedBuffer("ins", CharsetUtil.UTF_8), bucket()))
            .toBlocking()
            .single();
        ReferenceCountUtil.release(insertResponse);

        UpsertResponse upsertResponse = cluster()
                .<UpsertResponse>send(new UpsertRequest("perfUps", Unpooled.copiedBuffer("ups", CharsetUtil.UTF_8), bucket()))
                .toBlocking()
                .single();
        ReferenceCountUtil.release(upsertResponse);

        for (int i = 0; i < 5; i++) {
            GetResponse getResponse = cluster().<GetResponse>send(new GetRequest("perfIns", bucket()))
                    .toBlocking()
                    .single();
            ReferenceCountUtil.release(getResponse);

        }

        Thread.sleep(6000);

        List<CouchbaseEvent> events = eventSubscriber.getOnNextEvents();
        assertEquals(1, events.size());
        CoreNetworkLatencyMetricsEvent event = (CoreNetworkLatencyMetricsEvent) events.get(0);

        boolean hasInsert = false;
        boolean hasUpsert = false;
        boolean hasGet = false;
        for (AbstractLatencyMetricsEvent.Metric metric : event.metrics()) {
            if (metric.identifier().toString().endsWith("InsertRequest")) {
                hasInsert = true;
                assertEquals(1, metric.count());
            } else if (metric.identifier().toString().endsWith("UpsertRequest")) {
                hasUpsert = true;
                assertEquals(1, metric.count());
            } else if (metric.identifier().toString().endsWith("GetRequest")) {
                hasGet = true;
                assertEquals(5, metric.count());
            }
            assertTrue(metric.max() > 0);
            assertTrue(metric.min() > 0);
            assertTrue(metric.p50() <= metric.p90()
                    && metric.p90() <= metric.p95()
                    && metric.p95() <= metric.p99()
                    && metric.p99() <= metric.p999());
        }

        assertTrue(hasInsert);
        assertTrue(hasUpsert);
        assertTrue(hasGet);

        System.err.println(event.toString());
    }
}
