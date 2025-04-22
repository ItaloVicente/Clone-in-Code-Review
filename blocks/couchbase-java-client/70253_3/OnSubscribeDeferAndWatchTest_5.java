package com.couchbase.client.java.util;

import com.couchbase.client.deps.io.netty.buffer.ByteBuf;
import com.couchbase.client.deps.io.netty.buffer.ByteBufAllocator;
import com.couchbase.client.deps.io.netty.buffer.PooledByteBufAllocator;
import org.junit.Test;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.observers.TestSubscriber;
import rx.subjects.AsyncSubject;
import rx.subjects.Subject;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.couchbase.client.java.util.OnSubscribeDeferAndWatch.deferAndWatch;
import static org.junit.Assert.assertEquals;

public class OnSubscribeDeferAndWatchTest {

    private static final ByteBufAllocator ALLOCATOR = PooledByteBufAllocator.DEFAULT;

    @Test
    public void shouldFreeIfUnsubscribedEarly() throws Exception {
        final Subject<ByteBuf, ByteBuf> subject = AsyncSubject.create();

        TestSubscriber subscriber = TestSubscriber.create();
        deferAndWatch(new Func0<Observable<ByteBuf>>() {
            @Override
            public Observable<ByteBuf> call() {
                Observable
                    .timer(500, TimeUnit.MILLISECONDS)
                    .forEach(new Action1<Long>() {
                        @Override
                        public void call(Long ignored) {
                            subject.onNext(ALLOCATOR.buffer().writeInt(1234));
                            subject.onCompleted();
                        }
                    });

                return subject;
            }
        })
        .timeout(100, TimeUnit.MILLISECONDS)
        .subscribe(subscriber);

        subscriber.awaitTerminalEvent();
        subscriber.assertError(TimeoutException.class);

        Thread.sleep(1000);

        ByteBuf stored = subject.toBlocking().single();
        assertEquals(0, stored.refCnt()); // noone consumed it, but freed internally
    }

    @Test
    public void shouldNotDoubleFree() throws Exception {
        final Subject<ByteBuf, ByteBuf> subject = AsyncSubject.create();

        TestSubscriber<ByteBuf> subscriber = TestSubscriber.create();
        deferAndWatch(new Func0<Observable<ByteBuf>>() {
            @Override
            public Observable<ByteBuf> call() {
                Observable
                    .timer(500, TimeUnit.MILLISECONDS)
                    .forEach(new Action1<Long>() {
                        @Override
                        public void call(Long ignored) {
                            subject.onNext(ALLOCATOR.buffer().writeInt(1234));
                            subject.onCompleted();
                        }
                    });

                return subject;
            }
        })
        .toBlocking()
        .forEach(new Action1<ByteBuf>() {
            @Override
            public void call(ByteBuf byteBuf) {
                byteBuf.release();
            }
        });

        Thread.sleep(500);

        subject.forEach(new Action1<ByteBuf>() {
            @Override
            public void call(ByteBuf byteBuf) {
                assertEquals(0, byteBuf.refCnt());
            }
        });
    }

}
