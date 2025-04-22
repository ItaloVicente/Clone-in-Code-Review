package com.couchbase.client.core.node;

import com.couchbase.client.core.config.NodeInfo;

public class DefaultMemcachedHashingStrategy implements MemcachedHashingStrategy {

    public static DefaultMemcachedHashingStrategy INSTANCE = new DefaultMemcachedHashingStrategy();

    private DefaultMemcachedHashingStrategy() { }

    @Override
    public String hash(final NodeInfo info, final int repetition) {
        return info.hostname().getHostName() + "-" + repetition;
    }

}
