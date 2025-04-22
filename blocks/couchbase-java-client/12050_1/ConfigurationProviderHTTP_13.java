
package com.couchbase.client.vbucket;

import com.couchbase.client.vbucket.config.Bucket;

public interface ConfigurationProvider {

  Bucket getBucketConfiguration(String bucketname);

  void subscribe(String bucketName, Reconfigurable rec);

  void unsubscribe(String vbucketName, Reconfigurable rec);

  void shutdown();

  String getAnonymousAuthBucket();
}
