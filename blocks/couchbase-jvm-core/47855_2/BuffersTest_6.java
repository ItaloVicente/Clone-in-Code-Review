package com.couchbase.client.core.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;
import rx.Observable;
import rx.functions.Action1;
import rx.subjects.ReplaySubject;

import static org.junit.Assert.assertEquals;

public class BuffersTest {

    @Test
    public void shouldReleaseIfUnsubscribed() {
        ReplaySubject<ByteBuf> source = ReplaySubject.create();
        Observable<ByteBuf> wrapped = Buffers.wrapColdWithAutoRelease(source);

        for (int i = 0; i < 5; i++) {
            source.onNext(Unpooled.buffer());
        }
        source.onCompleted();

        source.toBlocking().forEach(new Action1<ByteBuf>() {
            @Override
            public void call(ByteBuf byteBuf) {
                assertEquals(1, byteBuf.refCnt());
            }
        });

        wrapped
            .take(2)
            .toBlocking()
            .forEach(new Action1<ByteBuf>() {
                @Override
                public void call(ByteBuf byteBuf) {
                    byteBuf.release();
                }
            });

        source.toBlocking().forEach(new Action1<ByteBuf>() {
            @Override
            public void call(ByteBuf byteBuf) {
                assertEquals(0, byteBuf.refCnt());
            }
        });

    }

}
