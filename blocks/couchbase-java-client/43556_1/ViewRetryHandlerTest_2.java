package com.couchbase.client.java.view;

import com.couchbase.client.core.message.view.ViewQueryResponse;
import com.couchbase.client.deps.io.netty.buffer.Unpooled;
import com.couchbase.client.deps.io.netty.util.CharsetUtil;
import org.junit.Test;
import rx.Observable;
import rx.Subscriber;
import rx.observers.TestSubscriber;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ViewRetryHandlerTest {

    @Test
    public void shouldNotRetrySuccess() {
        TestSubscriber<ViewQueryResponse> subscriber = new TestSubscriber<ViewQueryResponse>();

        final AtomicInteger subscriberCount = new AtomicInteger();
        Observable<ViewQueryResponse> observable = Observable.create(new Observable.OnSubscribe<ViewQueryResponse>() {
            @Override
            public void call(Subscriber<? super ViewQueryResponse> subscriber) {
                subscriberCount.incrementAndGet();
                ViewQueryResponse response = mock(ViewQueryResponse.class);
                when(response.responseCode()).thenReturn(200);
                subscriber.onNext(response);
                subscriber.onCompleted();
            }
        });

        ViewRetryHandler
            .retryOnCondition(observable)
            .subscribe(subscriber);

        subscriber.awaitTerminalEvent(1, TimeUnit.SECONDS);
        subscriber.assertNoErrors();
        assertEquals(1, subscriberCount.get());
    }

    @Test
    public void shouldRetryOnStatusCodes() {
        TestSubscriber<ViewQueryResponse> subscriber = new TestSubscriber<ViewQueryResponse>();

        final AtomicInteger subscriberCount = new AtomicInteger();
        Observable<ViewQueryResponse> observable = Observable.create(new Observable.OnSubscribe<ViewQueryResponse>() {
            @Override
            public void call(Subscriber<? super ViewQueryResponse> subscriber) {
                subscriberCount.incrementAndGet();
                ViewQueryResponse response = mock(ViewQueryResponse.class);
                int statusCode = subscriberCount.get() == 5 ? 200 : 300;
                when(response.responseCode()).thenReturn(statusCode);
                when(response.info()).thenReturn(Observable.just(Unpooled.copiedBuffer("{\"err\": true}",
                    CharsetUtil.UTF_8)));
                subscriber.onNext(response);
                subscriber.onCompleted();
            }
        });

        ViewRetryHandler
            .retryOnCondition(observable)
            .subscribe(subscriber);

        subscriber.awaitTerminalEvent(1, TimeUnit.SECONDS);
        subscriber.assertNoErrors();
        assertEquals(5, subscriberCount.get());
    }

    @Test
    public void shouldPassThroughExceptions() {
        TestSubscriber<ViewQueryResponse> subscriber = new TestSubscriber<ViewQueryResponse>();

        final AtomicInteger subscriberCount = new AtomicInteger();
        Observable<ViewQueryResponse> observable = Observable.create(new Observable.OnSubscribe<ViewQueryResponse>() {
            @Override
            public void call(Subscriber<? super ViewQueryResponse> subscriber) {
                subscriberCount.incrementAndGet();
                subscriber.onError(new IllegalStateException());
            }
        });

        ViewRetryHandler
            .retryOnCondition(observable)
            .subscribe(subscriber);

        subscriber.awaitTerminalEvent(1, TimeUnit.SECONDS);
        assertEquals(1, subscriber.getOnErrorEvents().size());
        assertTrue(subscriber.getOnErrorEvents().get(0) instanceof IllegalStateException);
        assertEquals(1, subscriberCount.get());
    }

    @Test
    public void shouldNotRetryDesignDocNotFound() {
        TestSubscriber<ViewQueryResponse> subscriber = new TestSubscriber<ViewQueryResponse>();

        final AtomicInteger subscriberCount = new AtomicInteger();
        Observable<ViewQueryResponse> observable = Observable.create(new Observable.OnSubscribe<ViewQueryResponse>() {
            @Override
            public void call(Subscriber<? super ViewQueryResponse> subscriber) {
                subscriberCount.incrementAndGet();
                ViewQueryResponse response = mock(ViewQueryResponse.class);
                when(response.responseCode()).thenReturn(404);
                when(response.info()).thenReturn(Observable.just(Unpooled.copiedBuffer("\n" +
                        "{\"error\":\"not_found\",\"reason\":\"Error opening view `al1l`, from set `default`, "
                        + "design document `_design/users`: {not_found,\\nmissing_named_view}\"}\n",
                    CharsetUtil.UTF_8)));
                subscriber.onNext(response);
                subscriber.onCompleted();
            }
        });

        ViewRetryHandler
            .retryOnCondition(observable)
            .subscribe(subscriber);

        subscriber.awaitTerminalEvent(1, TimeUnit.SECONDS);
        subscriber.assertNoErrors();
        assertEquals(404, subscriber.getOnNextEvents().get(0).responseCode());
        assertEquals(1, subscriberCount.get());
    }
}
