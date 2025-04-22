
package com.couchbase.client.core.env;

public final class QueryServiceConfig extends AbstractServiceConfig {

    private QueryServiceConfig(int minEndpoints, int maxEndpoints) {
        super(minEndpoints, maxEndpoints, false, 0);
    }

    public static QueryServiceConfig create(int minEndpoints, int maxEndpoints) {
        return new QueryServiceConfig(minEndpoints, maxEndpoints);
    }

    @Override
    public String toString() {
        return "QueryServiceConfig{" +
                "minEndpoints=" + minEndpoints() +
                ", maxEndpoints=" + maxEndpoints() +
                ", pipelined=" + isPipelined() +
                ", idleTime=" + idleTime() +
                '}';
    }

}
