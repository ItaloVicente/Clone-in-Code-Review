
package com.couchbase.client;

import com.couchbase.client.vbucket.ConfigurationProvider;
import com.couchbase.client.vbucket.config.Bucket;
import java.io.IOException;
import java.net.URI;
import java.util.List;

public class CouchbaseConnectionFactoryMock extends CouchbaseConnectionFactory {

  public CouchbaseConnectionFactoryMock(final List<URI> baseList,
      final String bucketName, String password, ConfigurationProvider cp)
      throws IOException {
    super(baseList, bucketName, password);

    this.configurationProvider = cp;
  }

  public Bucket getBucket(String bucketName) {
    return this.configurationProvider.getBucketConfiguration(bucketName);
  }

}
