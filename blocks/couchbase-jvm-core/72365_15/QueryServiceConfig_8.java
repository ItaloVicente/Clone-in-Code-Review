
package com.couchbase.client.core.env;

public final class KeyValueServiceConfig extends AbstractServiceConfig {

    private KeyValueServiceConfig(int minEndpoints, int maxEndpoints) {
        super(minEndpoints, maxEndpoints, true, NO_IDLE_TIME);
    }

    public static KeyValueServiceConfig create(int endpoints) {
        return new KeyValueServiceConfig(endpoints, endpoints);
    }

    @Override
    public String toString() {
        return "KeyValueServiceConfig{" +
                "minEndpoints=" + minEndpoints() +
                ", maxEndpoints=" + maxEndpoints() +
                ", pipelined=" + isPipelined() +
                ", idleTime=" + idleTime() +
                '}';
    }

}
