package com.couchbase.client.core.metrics;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.service.ServiceType;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class LatencyMetricIdentifier implements Comparable<LatencyMetricIdentifier> {

    private final String node;
    private final ServiceType serviceType;
    private final String suffix;

    public LatencyMetricIdentifier(String node, ServiceType serviceType, String suffix) {
        this.node = node;
        this.serviceType = serviceType;
        this.suffix = suffix;
    }

    @Override
    public String toString() {
        return node + "#" + serviceType + "#" + suffix;
    }

    @Override
    public int compareTo(LatencyMetricIdentifier o) {
        return toString().compareTo(o.toString());
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LatencyMetricIdentifier that = (LatencyMetricIdentifier) o;

        if (node != null ? !node.equals(that.node) : that.node != null) return false;
        if (serviceType != that.serviceType) return false;
        return !(suffix != null ? !suffix.equals(that.suffix) : that.suffix != null);

    }

    @Override
    public int hashCode() {
        int result = node != null ? node.hashCode() : 0;
        result = 31 * result + (serviceType != null ? serviceType.hashCode() : 0);
        result = 31 * result + (suffix != null ? suffix.hashCode() : 0);
        return result;
    }
}
