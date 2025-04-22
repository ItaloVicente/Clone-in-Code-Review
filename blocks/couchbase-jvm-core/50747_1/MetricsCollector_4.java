
package com.couchbase.client.core.metrics;

import com.couchbase.client.core.service.ServiceType;

public class MetricIdentifier {

    private final String node;
    private final ServiceType service;
    private final String suffix;

    public MetricIdentifier(String node, ServiceType service, String suffix) {
        this.node = node;
        this.service = service;
        this.suffix = suffix;
    }

    @Override
    public String toString() {
        return node + "." + service + "." + suffix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MetricIdentifier that = (MetricIdentifier) o;

        if (node != null ? !node.equals(that.node) : that.node != null) return false;
        if (service != that.service) return false;
        return !(suffix != null ? !suffix.equals(that.suffix) : that.suffix != null);

    }

    @Override
    public int hashCode() {
        int result = node != null ? node.hashCode() : 0;
        result = 31 * result + (service != null ? service.hashCode() : 0);
        result = 31 * result + (suffix != null ? suffix.hashCode() : 0);
        return result;
    }
}
