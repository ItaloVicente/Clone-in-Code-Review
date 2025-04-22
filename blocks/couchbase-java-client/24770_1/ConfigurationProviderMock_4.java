
package com.couchbase.client.vbucket;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.couchbase.client.vbucket.ConfigurationProvider;
import com.couchbase.client.vbucket.ConnectionException;
import com.couchbase.client.vbucket.ReconfigurableMock;
import com.couchbase.client.vbucket.config.Bucket;
import com.couchbase.client.vbucket.config.CacheConfig;
import com.couchbase.client.vbucket.config.Node;

public class ConfigurationProviderMock
  implements ConfigurationProvider {

  @Override
  public Bucket getBucketConfiguration(String bucketname) {

    CacheConfig config = new CacheConfig(1);
    config.setServers(Arrays.asList("badurl"+":8091"));
    URI streamingURI = URI.create("http://"+"badurl"+":8091");
    List<Node> nodes = new ArrayList<Node>();

    return new Bucket(bucketname, config, streamingURI, nodes);
  }

  @Override
  public void subscribe(String bucketName, Reconfigurable rec) {
    throw new ConnectionException();
  }

  @Override
  public void markForResubscribe(String bucketName, Reconfigurable rec) {}

  @Override
  public void unsubscribe(String vbucketName, Reconfigurable rec) {}

  @Override
  public void shutdown() {}

  @Override
  public String getAnonymousAuthBucket() {
    return "";
  }

  @Override
  public void finishResubscribe() {}

  @Override
  public Reconfigurable getReconfigurable() {
    return new ReconfigurableMock();
  }

  @Override
  public String getBucket() {
    return "resubscriber-bucket";
  }
}
