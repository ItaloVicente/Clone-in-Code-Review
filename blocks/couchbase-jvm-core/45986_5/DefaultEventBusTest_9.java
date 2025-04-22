package com.couchbase.client.core.event;

import org.junit.Test;
import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;
import rx.schedulers.TestScheduler;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class DefaultEventBusTest {

    @Test
    public void shouldPublishToSubscriber() throws Exception {
        TestScheduler testScheduler = Schedulers.test();
        EventBus eventBus = new DefaultEventBus(testScheduler);

        TestSubscriber<CouchbaseEvent> subscriber = new TestSubscriber<CouchbaseEvent>();
        eventBus.get().subscribe(subscriber);

        CouchbaseEvent event1 = mock(CouchbaseEvent.class);
        CouchbaseEvent event2 = mock(CouchbaseEvent.class);

        eventBus.publish(event1);
        eventBus.publish(event2);

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        assertEquals(2, subscriber.getOnNextEvents().size());
        assertEquals(0, subscriber.getOnErrorEvents().size());
        assertEquals(0, subscriber.getOnCompletedEvents().size());
    }

    @Test
    public void shouldNotBufferEventsBeforeSubscribe() throws Exception {
        TestScheduler testScheduler = Schedulers.test();
        EventBus eventBus = new DefaultEventBus(testScheduler);

        TestSubscriber<CouchbaseEvent> subscriber = new TestSubscriber<CouchbaseEvent>();


        CouchbaseEvent event1 = mock(CouchbaseEvent.class);
        CouchbaseEvent event2 = mock(CouchbaseEvent.class);

        eventBus.publish(event1);
        eventBus.get().subscribe(subscriber);
        eventBus.publish(event2);

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS);

        assertEquals(1, subscriber.getOnNextEvents().size());
        assertEquals(0, subscriber.getOnErrorEvents().size());
        assertEquals(0, subscriber.getOnCompletedEvents().size());
    }

}
