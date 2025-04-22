
package com.couchbase.client.java.analytics;
import java.util.concurrent.TimeUnit;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import rx.Observable;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public interface AsyncAnalyticsDeferredResultHandle {

    @InterfaceAudience.Private
    String getStatusHandleUri();


    @InterfaceAudience.Private
    String getResultHandleUri();

    Observable<AsyncAnalyticsQueryRow> rows();

    Observable<AsyncAnalyticsQueryRow> rows(long timeout, TimeUnit timeunit);

    Observable<String> status();

    Observable<String> status(long timeout, TimeUnit timeunit);
}
