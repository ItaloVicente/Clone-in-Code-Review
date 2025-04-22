package com.couchbase.client.core.env.resources;

import io.netty.channel.EventLoopGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import rx.Observable;
import rx.Subscriber;

public class IoPoolShutdownHook implements ShutdownHook {

    private final EventLoopGroup ioPool;
    private volatile boolean shutdown;

    public IoPoolShutdownHook(EventLoopGroup ioPool) {
        this.ioPool = ioPool;
        this.shutdown = false;
    }

    public Observable<Boolean> shutdown() {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(final Subscriber<? super Boolean> subscriber) {
                ioPool.shutdownGracefully().addListener(new GenericFutureListener() {
                    @Override
                    public void operationComplete(final Future future) throws Exception {
                        if (!subscriber.isUnsubscribed()) {
                            if (future.isSuccess()) {
                                subscriber.onNext(true);
                                shutdown = true;
                                subscriber.onCompleted();
                            } else {
                                subscriber.onError(future.cause());
                            }
                        }
                    }
                });
            }
        });
    }

    @Override
    public boolean isShutdown() {
        return shutdown;
    }
}
