
package com.couchbase.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.List;

import net.spy.memcached.auth.AuthDescriptor;
import net.spy.memcached.auth.PlainCallbackHandler;
import com.couchbase.client.vbucket.ConfigurationException;
import com.couchbase.client.vbucket.ConfigurationProvider;
import com.couchbase.client.vbucket.ConfigurationProviderHTTP;
import com.couchbase.client.vbucket.VBucketNodeLocator;
import net.spy.memcached.BinaryConnectionFactory;
import net.spy.memcached.DefaultHashAlgorithm;
import net.spy.memcached.FailureMode;
import net.spy.memcached.HashAlgorithm;
import net.spy.memcached.KetamaNodeLocator;
import net.spy.memcached.MemcachedConnection;
import net.spy.memcached.MemcachedNode;
import net.spy.memcached.NodeLocator;
import com.couchbase.client.vbucket.config.Config;
import com.couchbase.client.vbucket.config.ConfigType;

public class CouchbaseConnectionFactory extends BinaryConnectionFactory {

  public static final FailureMode DEFAULT_FAILURE_MODE = FailureMode.Retry;

  public static final HashAlgorithm DEFAULT_HASH =
    DefaultHashAlgorithm.KETAMA_HASH;

  public static final int DEFAULT_OP_QUEUE_LEN = 16384;

  private final ConfigurationProvider configurationProvider;
  private final String bucket;
  private final String pass;

  public CouchbaseConnectionFactory(final List<URI> baseList,
      final String bucketName, final String password)
    throws IOException {
    for (URI bu : baseList) {
      if (!bu.isAbsolute()) {
        throw new IllegalArgumentException("The base URI must be absolute");
      }
    }
    bucket = bucketName;
    pass = password;
    configurationProvider =
        new ConfigurationProviderHTTP(baseList, bucketName, password);
  }

  @Override
  public MemcachedConnection createConnection(List<InetSocketAddress> addrs)
    throws IOException {
    return new CouchbaseConnection(getReadBufSize(), this, addrs,
      getInitialObservers(), getFailureMode(), getOperationFactory());
  }

  @Override
  public NodeLocator createLocator(List<MemcachedNode> nodes) {
    Config config = getVBucketConfig();

    if (config == null) {
      throw new IllegalStateException("Couldn't get config");
    }

    if (config.getConfigType() == ConfigType.MEMBASE) {
      return new KetamaNodeLocator(nodes, getHashAlg());
    } else if (config.getConfigType() == ConfigType.MEMCACHE) {
      return new VBucketNodeLocator(nodes, getVBucketConfig());
    } else {
      throw new IllegalStateException("Unhandled locator type: "
          + config.getConfigType());
    }
  }

  public AuthDescriptor getAuthDescriptor() {
    if (!configurationProvider.getAnonymousAuthBucket().equals(bucket)
        && bucket != null) {
      return new AuthDescriptor(new String[] { "PLAIN" },
              new PlainCallbackHandler(bucket, pass));
    } else {
      return null;
    }
  }

  public String getBucketName() {
    return bucket;
  }

  public Config getVBucketConfig() {
    try {
      return configurationProvider.getBucketConfiguration(bucket).getConfig();
    } catch (ConfigurationException e) {
      return null;
    }
  }

  public ConfigurationProvider getConfigurationProvider() {
    return configurationProvider;
  }
}
