
package com.couchbase.client.java.analytics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class DefaultAnalyticsDeferredResultHandle implements AnalyticsDeferredResultHandle {

    private final AsyncAnalyticsDeferredResultHandle asyncHandle;

    public DefaultAnalyticsDeferredResultHandle(AsyncAnalyticsDeferredResultHandle asyncHandle) {
        this.asyncHandle = asyncHandle;
    }

    @Override
    public String getStatusHandleUri() {
        return this.asyncHandle.getStatusHandleUri();
    }

    @Override
    public String getResultHandleUri() {
        return this.asyncHandle.getResultHandleUri();
    }

    @Override
    public List<AnalyticsQueryRow> allRows() {
        List<AsyncAnalyticsQueryRow> rows = this.asyncHandle.rows().toList().toBlocking().single();
        List<AnalyticsQueryRow> res = new ArrayList<AnalyticsQueryRow>(rows.size());
        for (AsyncAnalyticsQueryRow row : rows) {
            res.add(new DefaultAnalyticsQueryRow(row));
        }
        return res;
    }

    @Override
    public Iterator<AnalyticsQueryRow> rows() {
        return this.allRows().iterator();
    }

    @Override
    public String status() {
        return this.asyncHandle.status().toBlocking().single();
    }

    @Override
    public String toString() {
        return "DefaultAnalyticsDeferredResultHandle{" +
                "statusUri='" + getStatusHandleUri() + '\'' +
                ", resultUri='" + getResultHandleUri() + '\'' +
                '}';
    }
}
