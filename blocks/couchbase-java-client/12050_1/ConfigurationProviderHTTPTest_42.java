
package com.couchbase.client.vbucket;

import com.couchbase.client.vbucket.ConfigurationProviderHTTP;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import net.spy.memcached.TestConfig;
import com.couchbase.client.vbucket.config.Bucket;


public class ConfigurationProviderHTTPDownNodeTest extends TestCase {
  private static final String REST_USER = "Administrator";
  private static final String REST_PASSWORD = "changeit";
  private ConfigurationProviderHTTP configProvider;
  private static final String DEFAULT_BUCKET_NAME = "default";

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    List<URI> baseList = new ArrayList<URI>();
    baseList.add(new URI("http://bogus:8091/pools"));
    baseList.add(new URI("http://bogustoo:8091/pools"));
    baseList.add(new URI("http://" + TestConfig.IPV4_ADDR + ":8091/pools"));
    baseList.add(new URI("http://morebogus:8091/pools"));
    configProvider = new ConfigurationProviderHTTP(baseList, REST_USER,
      REST_PASSWORD);
    assertNotNull(configProvider);
  }

  public void testGetBucketConfiguration() throws Exception {
    Bucket bucket = configProvider.getBucketConfiguration(DEFAULT_BUCKET_NAME);
    assertNotNull(bucket);
  }
}
