
package com.couchbase.client.core.node;

import com.couchbase.client.core.config.NodeInfo;
import com.couchbase.client.core.service.ServiceType;

public class StandardMemcachedHashingStrategy implements MemcachedHashingStrategy {

    public static StandardMemcachedHashingStrategy INSTANCE = new StandardMemcachedHashingStrategy();

    private StandardMemcachedHashingStrategy() { }

    @Override
    public String hash(final NodeInfo info, final int repetition) {
        String hostname = info.rawHostname();
        int port = info.services().get(ServiceType.BINARY);
        return hostname + ":" + port + "-" + repetition;
    }

}
