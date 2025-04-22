
package com.couchbase.client.core.env;

public final class ViewServiceConfig extends AbstractServiceConfig {

    private ViewServiceConfig(int minEndpoints, int maxEndpoints) {
        super(minEndpoints, maxEndpoints, false, 0);
    }

    public static ViewServiceConfig create(int minEndpoints, int maxEndpoints) {
        return new ViewServiceConfig(minEndpoints, maxEndpoints);
    }

    @Override
    public String toString() {
        return "ViewServiceConfig{" +
                "minEndpoints=" + minEndpoints() +
                ", maxEndpoints=" + maxEndpoints() +
                ", pipelined=" + isPipelined() +
                ", idleTime=" + idleTime() +
                '}';
    }

}
