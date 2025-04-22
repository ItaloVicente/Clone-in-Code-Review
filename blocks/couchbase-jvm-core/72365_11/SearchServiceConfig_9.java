
package com.couchbase.client.core.env;

public final class QueryServiceConfig extends AbstractServiceConfig {

    private QueryServiceConfig(int minEndpoints, int maxEndpoints, int idleTime) {
        super(minEndpoints, maxEndpoints, false, idleTime);
    }

    public static QueryServiceConfig create(int minEndpoints, int maxEndpoints) {
        return create(minEndpoints, maxEndpoints, 300);
    }

    public static QueryServiceConfig create(int minEndpoints, int maxEndpoints, int idleTime) {
        return new QueryServiceConfig(minEndpoints, maxEndpoints, idleTime);
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
