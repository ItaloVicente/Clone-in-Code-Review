package com.couchbase.client.core.operators;

import rx.Observable;
import rx.Subscriber;
import rx.functions.FuncN;
import rx.internal.operators.OnSubscribeCombineLatest;

import java.util.List;

public class OnSubscribeDynamicCombineLatest<T, R> implements Observable.OnSubscribe<R> {

    final List<? extends Observable<? extends T>> sources;

    final FuncN<? extends R> combinator;

    public OnSubscribeDynamicCombineLatest(final List<? extends Observable<? extends T>> sources,
        final FuncN<? extends R> combinator) {
        this.sources = sources;
        this.combinator = combinator;
    }

    @Override
    public void call(final Subscriber<? super R> child) {
        System.out.println(child);
    }

}
