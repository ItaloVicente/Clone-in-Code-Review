
package com.couchbase.client.core.env.resources;

import io.netty.util.ThreadDeathWatcher;
import rx.Observable;
import rx.Subscriber;

import java.util.concurrent.TimeUnit;

public class NettyShutdownHook implements ShutdownHook {

    private volatile boolean isReallyShutdown = false;

    @Override
    public Observable<Boolean> shutdown() {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(final Subscriber<? super Boolean> subscriber) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            isReallyShutdown = ThreadDeathWatcher.awaitInactivity(3, TimeUnit.SECONDS);
                            if (!subscriber.isUnsubscribed()) {
                                subscriber.onNext(isReallyShutdown);
                                subscriber.onCompleted();
                            }
                        } catch (Throwable e) {
                            if (!subscriber.isUnsubscribed()) {
                                subscriber.onError(e);
                            }
                        }
                    }
                }).start();
            }
        });
    }

    @Override
    public boolean isShutdown() {
        return isReallyShutdown;
    }

}
