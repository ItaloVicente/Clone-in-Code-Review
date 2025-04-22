package com.couchbase.client.core.node;

import com.couchbase.client.core.config.NodeInfo;

public interface MemcachedNodeHashingStrategy {

    String nodeHash(NodeInfo info, int repetition);

}
