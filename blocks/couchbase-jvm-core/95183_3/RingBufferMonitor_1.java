
package com.couchbase.client.core.tracing;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class RingBufferDiagnostics {

    public Map<RingBufferMonitor.QueryTypes, Integer> getCounts() {
        return counts;
    }

    public RingBufferDiagnostics(Map<RingBufferMonitor.QueryTypes, Integer> counts) {
        this.counts = counts;
    }

    private final Map<RingBufferMonitor.QueryTypes, Integer> counts;
}
