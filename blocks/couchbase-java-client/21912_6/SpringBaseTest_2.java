package com.couchbase.springframework;

import org.springframework.cache.support.SimpleCacheManager;
public class CouchbaseCacheManager extends SimpleCacheManager {
  @Override
  public CouchbaseCache getCache(String name) {
    return (CouchbaseCache) super.getCache(name);
  }
}
