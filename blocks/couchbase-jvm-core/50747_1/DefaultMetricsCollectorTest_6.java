package com.couchbase.client.core.metrics;


import com.couchbase.client.core.event.CouchbaseEvent;
import com.couchbase.client.core.event.DefaultEventBus;
import com.couchbase.client.core.event.EventBus;
import com.couchbase.client.core.service.ServiceType;
import org.junit.Test;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;
import rx.schedulers.TestScheduler;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class DefaultMetricsCollectorTest {

    @Test
    public void shouldFeedLatencyIntoEventBus() throws Exception {
        TestScheduler scheduler = Schedulers.test();
        EventBus eventBus = new DefaultEventBus(scheduler);

        TestSubscriber<CouchbaseEvent> testSubscriber = new TestSubscriber<CouchbaseEvent>();
        eventBus.get().subscribe(testSubscriber);

        MetricsCollector collector = new DefaultMetricsCollector(eventBus, 5, TimeUnit.SECONDS, TimeUnit.MICROSECONDS, scheduler);
        assertEquals(0, testSubscriber.getOnNextEvents().size());

        collector.recordLatency(new MetricIdentifier("127.0.0.1", ServiceType.BINARY, "get"), 500);
        collector.recordLatency(new MetricIdentifier("127.0.0.1", ServiceType.BINARY, "get"), 600);
        collector.recordLatency(new MetricIdentifier("127.0.0.1", ServiceType.BINARY, "set"), 200);

        collector.recordLatency(new MetricIdentifier("127.0.0.1", ServiceType.QUERY, "get"), 1200);

        scheduler.advanceTimeBy(6, TimeUnit.SECONDS);
        assertEquals(1, testSubscriber.getOnNextEvents().size());
    }
}
