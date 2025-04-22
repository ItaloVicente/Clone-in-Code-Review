
package com.couchbase.client.core.utils;

public class Rx2 {

    public static <T> io.reactivex.Observable<T> toV2Observable(rx.Observable<T> source) {
        return new ObservableV1ToObservableV2<T>(source);
    }

    public static <T> io.reactivex.Flowable<T> toV2Flowable(rx.Observable<T> source) {
        return new ObservableV1ToFlowableV2<T>(source);
    }

    public static <T> io.reactivex.Maybe<T> toV2Maybe(rx.Completable source) {
        return new CompletableV1ToMaybeV2<T>(source);
    }

    public static io.reactivex.Completable toV2Completable(rx.Completable source) {
        return new CompletableV1ToCompletableV2(source);
    }

    public static <T> io.reactivex.Maybe<T> toV2Maybe(rx.Single<T> source) {
        return new SingleV1ToMaybeV2<T>(source);
    }

    public static <T> io.reactivex.Single<T> toV2Single(rx.Single<T> source) {
        return new SingleV1ToSingleV2<T>(source);
    }

}
