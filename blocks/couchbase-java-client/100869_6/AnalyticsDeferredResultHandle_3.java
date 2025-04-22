
package com.couchbase.client.java.analytics;
import java.util.Iterator;
import java.util.List;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public interface AnalyticsDeferredResultHandle {

    @InterfaceAudience.Private
    String getStatusHandleUri();

    @InterfaceAudience.Private
    String getResultHandleUri();

    List<AnalyticsQueryRow> allRows();

    Iterator<AnalyticsQueryRow> rows();

    String status();

}
