
package com.couchbase.client.core.env;

public final class AnalyticsServiceConfig extends AbstractServiceConfig {

    private AnalyticsServiceConfig(int minEndpoints, int maxEndpoints, int idleTime) {
        super(minEndpoints, maxEndpoints, false, idleTime);
    }

    public static AnalyticsServiceConfig create(int minEndpoints, int maxEndpoints) {
        return create(minEndpoints, maxEndpoints, DEFAULT_IDLE_TIME);
    }

    public static AnalyticsServiceConfig create(int minEndpoints, int maxEndpoints, int idleTime) {
        if (idleTime > 0 && idleTime < 10) {
            throw new IllegalArgumentException("Idle time must either be 0 (disabled) or greater than 9 seconds");
        }

        return new AnalyticsServiceConfig(minEndpoints, maxEndpoints, idleTime);
    }

    @Override
    public String toString() {
        return "AnalyticsServiceConfig{" +
                "minEndpoints=" + minEndpoints() +
                ", maxEndpoints=" + maxEndpoints() +
                ", pipelined=" + isPipelined() +
                ", idleTime=" + idleTime() +
                '}';
    }

}
