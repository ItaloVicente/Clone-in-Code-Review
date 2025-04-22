package com.couchbase.client.vbucket.cccp;

import com.couchbase.client.vbucket.ConfigurationException;
import com.couchbase.client.vbucket.ConfigurationProvider;
import com.couchbase.client.vbucket.ConfigurationProviderHTTP;
import com.couchbase.client.vbucket.Reconfigurable;
import com.couchbase.client.vbucket.config.Bucket;
import com.couchbase.client.vbucket.config.ConfigurationParser;
import com.couchbase.client.vbucket.config.ConfigurationParserJSON;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.auth.AuthDescriptor;
import net.spy.memcached.auth.PlainCallbackHandler;
import net.spy.memcached.compat.SpyObject;
import net.spy.memcached.internal.OperationFuture;

import java.net.InetSocketAddress;
import java.net.URI;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class AdvancedConfigurationProvider extends SpyObject
  implements ConfigurationProvider {

  public static final int BINARY_PORT = 11210;

  public static final int BINARY_TIMEOUT = 3;

  private volatile List<URI> httpUris;

  private final String bucketName;

  private final String bucketPassword;

  private final AtomicReference<Bucket> bucketConfig;

  private final AtomicReference<ConfigurationProviderHTTP> httpConfigProvider;

  private final ConnectionFactoryBuilder builder;

  private final ConfigurationParser parser;

  public AdvancedConfigurationProvider(final List<URI> hosts,
    final String bucket, final String password) {
    this.httpUris = hosts;
    this.bucketName = bucket;
    this.bucketPassword = password;
    this.bucketConfig = new AtomicReference<Bucket>();
    this.httpConfigProvider = new AtomicReference<ConfigurationProviderHTTP>();
    this.parser = new ConfigurationParserJSON();

    builder = new ConnectionFactoryBuilder();
    builder.setProtocol(ConnectionFactoryBuilder.Protocol.BINARY);
    if (bucketName != getAnonymousAuthBucket()) {
      builder.setAuthDescriptor(new AuthDescriptor(
        new String[] {},
        new PlainCallbackHandler(bucketName, bucketPassword)
      ));
    }
  }

  @Override
  public Bucket getBucketConfiguration(String bucketname) {
    if (bucketConfig.get() != null) {
      return bucketConfig.get();
    }

    Bucket config = bootstrapAndFetchBucketConfig();
    bucketConfig.set(config);
    return config;
  }

  private Bucket bootstrapAndFetchBucketConfig() {
    try {
      return fetchConifgFromServers();
    } catch (Exception e) {
      getLogger().info("Could not bootstrap from binary port, falling back "
        + "to HTTP.", e);
      return fallBackToHttp();
    }
  }

  private Bucket fetchConifgFromServers() throws Exception {
    for (URI uri : httpUris) {
      InetSocketAddress addr = new InetSocketAddress(uri.getHost(),
        BINARY_PORT);
      ConfigAwareMemcachedClient client;
      try {
        client = new ConfigAwareMemcachedClient(builder.build(),
          Arrays.asList(addr));
      } catch (Exception ex) {
        getLogger().debug("Could not connect to binary config.", ex);
        continue;
      }
      OperationFuture<String> future = client.asyncGetConfig();
      String rawConfig = future.get();
      if (rawConfig == null || rawConfig.isEmpty()) {
        getLogger().debug("No binary config found or empty.");
        continue;
      }
      client.shutdown();
      return parser.parseBucket(rawConfig);
    }

    throw new ConfigurationException("Could not find a single "
      + "configuration over binary protocol.");
  }

  private Bucket fallBackToHttp() {
    if (!hasActiveHttpProvider()) {
      httpConfigProvider.set(new ConfigurationProviderHTTP(httpUris, bucketName,
        bucketPassword));
    }

    return getHttpConfigProvider().getBucketConfiguration(bucketName);
  }

  @Override
  public void updateBucket(final String name, final Bucket config) {
    if (!name.equals(bucketName)) {
      throw new IllegalArgumentException("Bucket name must be equal.");
    }

    bucketConfig.set(config);
    if (hasActiveHttpProvider()) {
      getHttpConfigProvider().updateBucket(name, config);
    }
  }

  @Override
  public void updateBucket(final String config) {
    try {
      updateBucket(getBucket(), parser.parseBucket(config));
    } catch (Exception e) {
      getLogger().info("Got new config to update, but could not decode it. "
        + "Staying with old one.");
    }
  }

  @Override
  public void subscribe(String name, Reconfigurable rec) {
    if (hasActiveHttpProvider()) {
      getHttpConfigProvider().subscribe(name, rec);
    }
  }

  @Override
  public void markForResubscribe(String name, Reconfigurable rec) {
    if (hasActiveHttpProvider()) {
      getHttpConfigProvider().markForResubscribe(name, rec);
    }
  }

  @Override
  public void unsubscribe(String name, Reconfigurable rec) {
    if (hasActiveHttpProvider()) {
      getHttpConfigProvider().markForResubscribe(name, rec);
    }
  }

  @Override
  public void shutdown() {
    if (hasActiveHttpProvider()) {
      getHttpConfigProvider().shutdown();
    }
  }

  @Override
  public String getAnonymousAuthBucket() {
    return ConfigurationProviderHTTP.ANONYMOUS_AUTH_BUCKET;
  }

  @Override
  public void finishResubscribe() {
    if (hasActiveHttpProvider()) {
      getHttpConfigProvider().finishResubscribe();
    }
  }

  @Override
  public Reconfigurable getReconfigurable() {
    if (hasActiveHttpProvider()) {
      return getHttpConfigProvider().getReconfigurable();
    }
    return null;
  }

  @Override
  public String getBucket() {
    return bucketName;
  }

  @Override
  public void updateBaseListFromConfig(List<URI> baseList) {
    httpUris = baseList;

    if (hasActiveHttpProvider()) {
      getHttpConfigProvider().updateBaseListFromConfig(baseList);
    }
  }

  private boolean hasActiveHttpProvider() {
    return httpConfigProvider.get() != null;
  }

  ConfigurationProviderHTTP getHttpConfigProvider() {
    return httpConfigProvider.get();
  }

}
