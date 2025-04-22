
package com.couchbase.client.core.env;

public abstract class AbstractServiceConfig {

    private final int minEndpoints;
    private final int maxEndpoints;
    private final boolean pipelined;
    private final int idleTime;

    protected AbstractServiceConfig(int minEndpoints, int maxEndpoints, boolean pipelined, int idleTime) {
        this.minEndpoints = minEndpoints;
        this.maxEndpoints = maxEndpoints;
        this.pipelined = pipelined;
        this.idleTime = idleTime;
    }

    public int minEndpoints() {
        return minEndpoints;
    }

    public int maxEndpoints() {
        return maxEndpoints;
    }

    public boolean isPipelined() {
        return pipelined;
    }

    public int idleTime() {
        return idleTime;
    }

    @Override
    public String toString() {
        return "AbstractServiceConfig{" +
                "minEndpoints=" + minEndpoints +
                ", maxEndpoints=" + maxEndpoints +
                ", pipelined=" + pipelined +
                ", idleTime=" + idleTime +
                '}';
    }
}
