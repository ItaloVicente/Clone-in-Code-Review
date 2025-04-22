package com.couchbase.client.core.node;

import com.couchbase.client.core.config.NodeInfo;
import com.couchbase.client.core.service.ServiceType;

public class LegacyMemcachedNodeHashingStrategy implements MemcachedNodeHashingStrategy {

    @Override
    public String nodeHash(final NodeInfo info, final int repetition) {
        String hostname = info.hostname().getHostAddress();
        if (hostname.startsWith("/")) {
            hostname = hostname.substring(1);
        }
        int port = info.services().get(ServiceType.BINARY);
        return hostname + ":" + port + "-" + repetition;
    }
}
