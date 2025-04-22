
package com.couchbase.client.java.analytics;

import java.util.concurrent.TimeUnit;

import rx.Observable;

public interface AsyncAnalyticsHandle {

    Observable<AsyncAnalyticsQueryRow> rows();

    Observable<AsyncAnalyticsQueryRow> rows(long timeout, TimeUnit timeunit);

    Observable<String> status();

    Observable<String> status(long timeout, TimeUnit timeunit);
}
