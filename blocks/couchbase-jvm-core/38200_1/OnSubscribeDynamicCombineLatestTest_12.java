package com.couchbase.client.core.operators;

import org.junit.Test;
import rx.Observable;
import rx.Subscriber;
import rx.functions.FuncN;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class OnSubscribeDynamicCombineLatestTest {

    @Test
    public void shouldPushValuesOfOne() {
        List<Observable<Long>> sources = new ArrayList<Observable<Long>>();
        sources.add(Observable.interval(1, TimeUnit.MILLISECONDS));
        OnSubscribeDynamicCombineLatest<Long, String> on = new OnSubscribeDynamicCombineLatest<Long, String>(sources, new FuncN<String>() {
            @Override
            public String call(Object... args) {
                StringBuilder sb = new StringBuilder();
                for (Object arg : args) {
                    sb.append((String) arg);
                }
                return sb.toString();
            }
        });

        on.call(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
            }
        });
    }

    @Test
    public void shouldPushValuesOfMany() {

    }

    @Test
    public void shouldRecognizeAddingOfSources() {

    }

    @Test
    public void shouldRecognizeRemovingOfSources() {

    }
}
