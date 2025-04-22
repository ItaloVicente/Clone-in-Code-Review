
package com.couchbase.client.core.env;

public final class KeyValueServiceConfig extends AbstractServiceConfig {

    private KeyValueServiceConfig(int minEndpoints, int maxEndpoints) {
        super(minEndpoints, maxEndpoints, true, 0);
    }

    public static KeyValueServiceConfig create(int endpoints) {
        return new KeyValueServiceConfig(endpoints, endpoints);
    }

}
