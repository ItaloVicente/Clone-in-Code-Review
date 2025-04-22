package com.couchbase.client.java.util;

import com.couchbase.client.deps.io.netty.util.ReferenceCounted;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.exceptions.Exceptions;
import rx.functions.Func0;
import rx.observers.Subscribers;
import rx.subjects.AsyncSubject;

import java.util.concurrent.atomic.AtomicReference;

public class OnSubscribeDeferAndWatch<T> implements Observable.OnSubscribe<T> {

    public static <T> Observable<T> deferAndWatch(Func0<? extends Observable<? extends T>> observableFactory) {
        return Observable.create(new OnSubscribeDeferAndWatch<T>(observableFactory));
    }

    private final Func0<? extends Observable<? extends T>> observableFactory;

    private OnSubscribeDeferAndWatch(Func0<? extends Observable<? extends T>> observableFactory) {
        this.observableFactory = observableFactory;
    }

    @Override
    public void call(Subscriber<? super T> s) {

        Observable<? extends T> o;
        try {
            o = observableFactory.call();
        } catch (Throwable t) {
            Exceptions.throwOrReport(t, s);
            return;
        }

        if (!(o instanceof AsyncSubject)) {
            Exceptions.throwOrReport(
                new IllegalStateException("Only AsyncSubjects are allowed with DeferAndClean (is "
                    + o.getClass().getSimpleName() + ")"), s);
            return;
        }

        final AtomicReference<Subscription> sr = new AtomicReference<Subscription>();
        sr.set(o.unsafeSubscribe(Subscribers.wrap(s)));

        o.subscribe(new Subscriber<T>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(T t) {
                if (t != null && sr.get().isUnsubscribed() && t instanceof ReferenceCounted) {
                    ReferenceCounted rc = (ReferenceCounted) t;
                    if (rc.refCnt() > 0) {
                        rc.release();
                    }
                }
            }
        });
    }
}
