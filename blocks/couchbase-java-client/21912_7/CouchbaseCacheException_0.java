package com.couchbase.springframework;

import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.CouchbaseConnectionFactoryBuilder;
import com.couchbase.client.TapClient;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.naming.ConfigurationException;
import net.spy.memcached.tapmessage.ResponseMessage;
import org.springframework.cache.Cache;


public class CouchbaseCache implements Cache {

  private String name;
  private CouchbaseClient c;
  private TapClient tapClient;

  public CouchbaseCache(String name, List<URI> baseList,
          String bucketName, String password)
    throws IOException {
    this.name = name;
    CouchbaseConnectionFactoryBuilder cfb = new
            CouchbaseConnectionFactoryBuilder();
    c = new CouchbaseClient(cfb.buildCouchbaseConnection(baseList,
            bucketName, password));
    tapClient = new TapClient(baseList, bucketName, password);
    clear();
  }

  @Override
  public void clear() {
    try {
      tapClient.tapDump("");
    } catch (IOException ex) {
      throw new CouchbaseCacheException("Exception in Couchbase Cache clear ",
              ex);
    } catch (ConfigurationException ex) {
      throw new CouchbaseCacheException("Exception in Couchbase Cache clear ",
              ex);
    }
    while(tapClient.hasMoreMessages()) {
      ResponseMessage response = tapClient.getNextMessage();
      if (response != null) {
        c.delete(response.getKey());
      }
    }
  }

  @Override
  public void evict(Object key) {
    c.delete(key.toString());
  }

  @Override
  public Cache.ValueWrapper get(final Object key) {
    return (new Cache.ValueWrapper() {
      @Override
      public Object get() {
        return c.get(key.toString());
      }
    });
  }

  @Override
  public Object getNativeCache() {
    return this;
  }

  @Override
  public void put(Object key, Object value) {
    try {
      c.set(key.toString(), 0, value).get();
    } catch (InterruptedException ex) {
      throw new CouchbaseCacheException("Exception in Couchbase Cache put for "
              + "key = " + key + "value = " + value, ex);
    } catch (ExecutionException ex) {
      throw new CouchbaseCacheException("Exception in Couchbase Cache put for "
              + "key = " + key + "value = " + value, ex);
    }
  }

  @Override
  public String getName() {
    return name;
  }
}
