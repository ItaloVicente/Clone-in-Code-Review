
package com.couchbase.client.core.utils;

import io.reactivex.MaybeObserver;

final class SingleV1ToMaybeV2<T> extends io.reactivex.Maybe<T> {

    final rx.Single<T> source;

    SingleV1ToMaybeV2(rx.Single<T> source) {
        this.source = source;
    }

    @Override
    protected void subscribeActual(MaybeObserver<? super T> observer) {
        SourceSingleSubscriber<T> parent = new SourceSingleSubscriber<T>(observer);
        observer.onSubscribe(parent);
        source.subscribe(parent);
    }

    static final class SourceSingleSubscriber<T> extends rx.SingleSubscriber<T>
        implements io.reactivex.disposables.Disposable {

        final io.reactivex.MaybeObserver<? super T> observer;

        SourceSingleSubscriber(io.reactivex.MaybeObserver<? super T> observer) {
            this.observer = observer;
        }

        @Override
        public void onSuccess(T value) {
            if (value == null) {
                observer.onError(new NullPointerException(
                    "The upstream 1.x Single signalled a null value which is not supported in 2.x"));
            } else {
                observer.onSuccess(value);
            }
        }

        @Override
        public void onError(Throwable error) {
            observer.onError(error);
        }

        @Override
        public void dispose() {
            unsubscribe();
        }

        @Override
        public boolean isDisposed() {
            return isUnsubscribed();
        }
    }
}
