
package com.couchbase.client.java.analytics;
import java.util.Iterator;
import java.util.List;

import com.couchbase.client.core.BackpressureException;
import com.couchbase.client.core.CouchbaseException;
import com.couchbase.client.core.RequestCancelledException;
import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.error.CouchbaseOutOfMemoryException;
import com.couchbase.client.java.error.DocumentDoesNotExistException;
import com.couchbase.client.java.error.QueryExecutionException;
import com.couchbase.client.java.error.TemporaryFailureException;

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
