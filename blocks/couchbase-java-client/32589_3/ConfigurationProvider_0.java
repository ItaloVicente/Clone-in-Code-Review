
package com.couchbase.client.vbucket.provider;

import com.couchbase.client.CouchbaseConnection;
import com.couchbase.client.CouchbaseConnectionFactory;
import com.couchbase.client.vbucket.ConfigurationException;
import com.couchbase.client.vbucket.ConfigurationProviderHTTP;
import com.couchbase.client.vbucket.Reconfigurable;
import com.couchbase.client.vbucket.config.Bucket;
import com.couchbase.client.vbucket.config.ConfigurationParser;
import com.couchbase.client.vbucket.config.ConfigurationParserJSON;
import net.spy.memcached.BroadcastOpFactory;
import net.spy.memcached.MemcachedNode;
import net.spy.memcached.auth.AuthThreadMonitor;
import net.spy.memcached.compat.SpyObject;
import net.spy.memcached.ops.Operation;
import net.spy.memcached.ops.OperationCallback;
import net.spy.memcached.ops.OperationStatus;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class BucketConfigurationProvider extends SpyObject
  implements ConfigurationProvider, Reconfigurable {

  private static final int DEFAULT_BINARY_PORT = 11210;

  private final AtomicReference<Bucket> config;
  private final List<URI> seedNodes;
  private final String bucket;
  private final String password;
  private final CouchbaseConnectionFactory connectionFactory;
  private final ConfigurationParser configurationParser;
  private final ConfigurationProviderHTTP httpProvider;
  private volatile boolean isBinary;

  public BucketConfigurationProvider(final List<URI> seedNodes,
    final String bucket, final String password,
    final CouchbaseConnectionFactory connectionFactory) {
    config = new AtomicReference<Bucket>();
    configurationParser = new ConfigurationParserJSON();
    httpProvider = new ConfigurationProviderHTTP(
      seedNodes, bucket, password
    );

    this.seedNodes = seedNodes;
    this.bucket = bucket;
    this.password = password;
    this.connectionFactory = connectionFactory;
  }

  @Override
  public Bucket bootstrap() throws ConfigurationException {
    if (!bootstrapBinary() && !bootstrapHttp()) {
      throw new ConfigurationException("Could not fetch a valid Bucket "
        + "configuration.");
    }

    monitorBucket();
    return config.get();
  }

  boolean bootstrapBinary() {
    CouchbaseConnectionFactory cf = connectionFactory;
    List<InetSocketAddress> nodes =
      new ArrayList<InetSocketAddress>(seedNodes.size());
    for (URI seedNode : seedNodes) {
      nodes.add(new InetSocketAddress(seedNode.getHost(), DEFAULT_BINARY_PORT));
    }

    CouchbaseConnection connection = null;
    try {
      connection = new CouchbaseConnection(
        cf.getReadBufSize(), cf, nodes, cf.getInitialObservers(),
        cf.getFailureMode(), cf.getOperationFactory()
      );

      AuthThreadMonitor monitor = new AuthThreadMonitor();
      List<MemcachedNode> connectedNodes = new ArrayList<MemcachedNode>(
        connection.getLocator().getAll());
      for (MemcachedNode node : connectedNodes) {
        monitor.authConnection(connection, cf.getOperationFactory(),
          cf.getAuthDescriptor(), node);
      }

      final List<String> configs = Collections.synchronizedList(
        new ArrayList<String>());
      CountDownLatch blatch = connection.broadcastOperation(
        new BroadcastOpFactory() {
          @Override
          public Operation newOp(MemcachedNode n, final CountDownLatch latch) {
            return new GetConfigOperationImpl(new OperationCallback() {
              @Override
              public void receivedStatus(OperationStatus status) {
                if (status.isSuccess()) {
                  configs.add(status.getMessage());
                }
              }

              @Override
              public void complete() {
                latch.countDown();
              }
            });
          }
        }
      );
      blatch.await(cf.getOperationTimeout(), TimeUnit.MILLISECONDS);

      if (configs.isEmpty()) {
        getLogger().debug("Could not load a single config over binary.");
        return false;
      }

      String appliedConfig = connection.replaceConfigWildcards(configs.get(0));
      Bucket config = configurationParser.parseBucket(appliedConfig);
      setConfig(config);
      isBinary = true;
      return true;
    } catch(Exception ex) {
      getLogger().info("Could not fetch config from binary seed nodes.", ex);
      return false;
    } finally {
      if (connection != null) {
        try {
          connection.shutdown();
        } catch (IOException ex) {
          getLogger().info("Could not shutdown the connection", ex);
        }
      }
    }
  }

  boolean bootstrapHttp() {
    try {
      Bucket config = httpProvider.getBucketConfiguration(bucket);
      setConfig(config);
      return true;
    } catch(Exception ex) {
      getLogger().info("Could not fetch config from http seed nodes.", ex);
      return false;
    }
  }

  private void monitorBucket() {
    if (!isBinary) {
      httpProvider.subscribe(bucket, this);
    }
  }

  @Override
  public void reconfigure(final Bucket bucket) {
    setConfig(bucket);
  }

  @Override
  public Bucket getConfig() {
    return config.get();
  }

  @Override
  public void setConfig(final Bucket config) {
    this.config.set(config);
  }

  @Override
  public void signalOutdated() {
    if (isBinary) {
    } else {
    }
  }

  @Override
  public void shutdown() {
    httpProvider.shutdown();
  }

}
