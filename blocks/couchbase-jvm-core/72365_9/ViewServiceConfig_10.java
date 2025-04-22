
package com.couchbase.client.core.env;

public final class SearchServiceConfig extends AbstractServiceConfig {

    private SearchServiceConfig(int minEndpoints, int maxEndpoints) {
        super(minEndpoints, maxEndpoints, false, 0);
    }

    public static SearchServiceConfig create(int minEndpoints, int maxEndpoints) {
        return new SearchServiceConfig(minEndpoints, maxEndpoints);
    }

    @Override
    public String toString() {
        return "SearchServiceConfig{" +
                "minEndpoints=" + minEndpoints() +
                ", maxEndpoints=" + maxEndpoints() +
                ", pipelined=" + isPipelined() +
                ", idleTime=" + idleTime() +
                '}';
    }
}
