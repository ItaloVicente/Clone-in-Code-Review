package com.couchbase.client.core.config.refresher;

import com.couchbase.client.core.cluster.Cluster;
import com.couchbase.client.core.config.BucketConfig;
import com.couchbase.client.core.message.CouchbaseResponse;
import com.couchbase.client.core.message.ResponseStatus;
import com.couchbase.client.core.message.config.BucketStreamingRequest;
import com.couchbase.client.core.message.config.BucketStreamingResponse;
import com.couchbase.client.core.util.Resources;
import org.junit.Test;
import rx.Observable;
import rx.functions.Action1;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HttpRefresherTest {

    @Test
    public void shouldPublishNewBucketConfiguration() throws Exception {
        Cluster cluster = mock(Cluster.class);

        Observable<String> configStream = Observable.from(
            Resources.read("stream1.json", this.getClass()),
            Resources.read("stream2.json", this.getClass()),
            Resources.read("stream3.json", this.getClass())
        );
        Observable<CouchbaseResponse> response = Observable.from((CouchbaseResponse)
            new BucketStreamingResponse(configStream, ResponseStatus.SUCCESS, null)
        );
        when(cluster.send(isA(BucketStreamingRequest.class))).thenReturn(response);

        HttpRefresher refresher = new HttpRefresher(cluster);

        final CountDownLatch latch = new CountDownLatch(3);
        refresher.configs().subscribe(new Action1<BucketConfig>() {
            @Override
            public void call(BucketConfig bucketConfig) {
                assertEquals("default", bucketConfig.name());
                latch.countDown();
            }
        });

        Observable<Boolean> observable = refresher.registerBucket("default", "");
        assertTrue(observable.toBlockingObservable().single());
        assertTrue(latch.await(3, TimeUnit.SECONDS));
    }

    @Test
    public void shouldFallbackToVerboseIfTerseFails() throws Exception {
        Cluster cluster = mock(Cluster.class);

        Observable<String> configStream = Observable.from(
            Resources.read("stream1.json", this.getClass()),
            Resources.read("stream2.json", this.getClass()),
            Resources.read("stream3.json", this.getClass())
        );

        Observable<CouchbaseResponse> failingResponse = Observable.error(new Exception("failed"));
        Observable<CouchbaseResponse> successResponse = Observable.from((CouchbaseResponse)
                new BucketStreamingResponse(configStream, ResponseStatus.SUCCESS, null)
        );
        when(cluster.send(isA(BucketStreamingRequest.class))).thenReturn(failingResponse);
        when(cluster.send(isA(BucketStreamingRequest.class))).thenReturn(successResponse);

        HttpRefresher refresher = new HttpRefresher(cluster);

        final CountDownLatch latch = new CountDownLatch(3);
        refresher.configs().subscribe(new Action1<BucketConfig>() {
            @Override
            public void call(BucketConfig bucketConfig) {
                assertEquals("default", bucketConfig.name());
                latch.countDown();
            }
        });

        Observable<Boolean> observable = refresher.registerBucket("default", "");
        assertTrue(observable.toBlockingObservable().single());
        assertTrue(latch.await(3, TimeUnit.SECONDS));
    }

}
