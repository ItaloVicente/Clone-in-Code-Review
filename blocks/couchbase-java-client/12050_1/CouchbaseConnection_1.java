
package com.couchbase.client;

import java.util.concurrent.Future;

import net.spy.memcached.CASValue;
import net.spy.memcached.MemcachedClientIF;
import net.spy.memcached.transcoders.Transcoder;

public interface CouchbaseClientIF extends MemcachedClientIF {

  Future<CASValue<Object>> asyncGetAndLock(final String key, int exp);

  <T> Future<CASValue<T>> asyncGetAndLock(final String key, int exp,
      final Transcoder<T> tc);

  <T> CASValue<T> getAndLock(String key, int exp, Transcoder<T> tc);

  CASValue<Object> getAndLock(String key, int exp);

  int getNumVBuckets();
}
