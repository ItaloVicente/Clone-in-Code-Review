package com.couchbase.client.core.node;

import com.couchbase.client.core.config.NodeInfo;

public class DefaultMemcachedNodeHashingStrategy implements MemcachedNodeHashingStrategy {

    public static DefaultMemcachedNodeHashingStrategy INSTANCE = new DefaultMemcachedNodeHashingStrategy();

    @Override
    public String nodeHash(final NodeInfo info, final int repetition) {
        return info.hostname().getHostName() + "-" + repetition;
    }

}
