
package com.couchbase.client.core.env;

public abstract class AbstractServiceConfig {

    public static final int NO_IDLE_TIME = 0;
    public static final int DEFAULT_IDLE_TIME = 300;

    private final int minEndpoints;
    private final int maxEndpoints;
    private final boolean pipelined;
    private final int idleTime;

    protected AbstractServiceConfig(int minEndpoints, int maxEndpoints, boolean pipelined, int idleTime) {
        if (minEndpoints < 0 || maxEndpoints < 0) {
            throw new IllegalArgumentException("The minEndpoints and maxEndpoints must not be negative");
        }
        if (maxEndpoints == 0) {
            throw new IllegalArgumentException("The maxEndpoints must be greater than 0");
        }
        if (maxEndpoints < minEndpoints) {
            throw new IllegalArgumentException("The maxEndpoints must not be smaller than mindEndpoints");
        }

        if (pipelined && (minEndpoints != maxEndpoints)) {
            throw new IllegalArgumentException("Pipelining and non-fixed size of endpoints is "
                + "currently not supported.");
        }

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
