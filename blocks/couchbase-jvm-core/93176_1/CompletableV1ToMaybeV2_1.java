
package com.couchbase.client.core.utils;

final class CompletableV1ToMaybeV2<T> extends io.reactivex.Maybe<T> {

    final rx.Completable source;

    CompletableV1ToMaybeV2(rx.Completable source) {
        this.source = source;
    }

    @Override
    protected void subscribeActual(io.reactivex.MaybeObserver<? super T> observer) {
        source.subscribe(new SourceCompletableSubscriber<T>(observer));
    }

    static final class SourceCompletableSubscriber<T>
        implements rx.CompletableSubscriber, io.reactivex.disposables.Disposable {

        final io.reactivex.MaybeObserver<? super T> observer;

        rx.Subscription s;

        SourceCompletableSubscriber(io.reactivex.MaybeObserver<? super T> observer) {
            this.observer = observer;
        }

        @Override
        public void onSubscribe(rx.Subscription d) {
            this.s = d;
            observer.onSubscribe(this);
        }

        @Override
        public void onCompleted() {
            observer.onComplete();
        }

        @Override
        public void onError(Throwable error) {
            observer.onError(error);
        }

        @Override
        public void dispose() {
            s.unsubscribe();
        }

        @Override
        public boolean isDisposed() {
            return s.isUnsubscribed();
        }
    }
}
