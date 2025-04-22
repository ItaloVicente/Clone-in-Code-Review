package com.couchbase.client.core.node;

import com.couchbase.client.core.config.NodeInfo;

public interface MemcachedHashingStrategy {

    String hash(NodeInfo info, int repetition);

}
