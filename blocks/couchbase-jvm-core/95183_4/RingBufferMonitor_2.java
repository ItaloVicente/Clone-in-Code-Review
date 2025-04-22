
package com.couchbase.client.core.tracing;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.service.ServiceType;

import java.util.Iterator;
import java.util.Map;

@InterfaceAudience.Public
@InterfaceStability.Experimental
public class RingBufferDiagnostics {
    private final Map<ServiceType, Integer> counts;
    private final int countNonService;

    @InterfaceAudience.Public
    @InterfaceStability.Experimental
    public Map<ServiceType, Integer> counts() {
        return counts;
    }

    @InterfaceAudience.Public
    @InterfaceStability.Experimental
    public int countNonService() {
        return countNonService;
    }

    @InterfaceAudience.Public
    @InterfaceStability.Experimental
    public int totalCount() {
        int total = countNonService;
        for (Map.Entry<ServiceType, Integer> entry : counts.entrySet()) {
            total += entry.getValue();
        }
        return total;
    }

    @InterfaceAudience.Public
    @InterfaceStability.Experimental
    public RingBufferDiagnostics(Map<ServiceType, Integer> counts, int countNonService) {
        this.counts = counts;
        this.countNonService = countNonService;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int total = countNonService;

        for (Map.Entry<ServiceType, Integer> entry : counts.entrySet()) {
            total += entry.getValue();
            sb.append(entry.getKey().name())
                    .append('=')
                    .append(entry.getValue())
                    .append(' ');
        }

        sb.append("other=")
                .append(countNonService)
                .append(" total=")
                .append(total);
        return sb.toString();
    }
}
