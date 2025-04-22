package com.couchbase.client.core.config;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.SortedMap;

@JsonDeserialize(as = DefaultMemcachedBucketConfig.class)
public interface MemcachedBucketConfig extends BucketConfig {

    SortedMap<Long, NodeInfo> ketamaNodes();

}
