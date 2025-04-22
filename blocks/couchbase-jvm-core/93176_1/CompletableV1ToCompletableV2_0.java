
package com.couchbase.client.core.utils;

final class CompletableV1ToCompletableV2 extends io.reactivex.Completable {

    final rx.Completable source;

    CompletableV1ToCompletableV2(rx.Completable source) {
        this.source = source;
    }

    @Override
    protected void subscribeActual(io.reactivex.CompletableObserver observer) {
        source.subscribe(new SourceCompletableSubscriber(observer));
    }

    static final class SourceCompletableSubscriber
        implements rx.CompletableSubscriber, io.reactivex.disposables.Disposable {

        final io.reactivex.CompletableObserver observer;

        rx.Subscription s;

        SourceCompletableSubscriber(io.reactivex.CompletableObserver observer) {
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
