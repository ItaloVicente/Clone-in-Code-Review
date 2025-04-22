
package com.couchbase.client.vbucket.config;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import com.couchbase.client.vbucket.ConfigurationException;

public class Pool {
  private final String name;
  private final URI uri;
  private final URI streamingUri;
  private URI bucketsUri;
  private final AtomicReference<Map<String, Bucket>> currentBuckets =
      new AtomicReference<Map<String, Bucket>>();

  public Pool(String name, URI uri, URI streamingUri) {
    this.name = name;
    this.uri = uri;
    this.streamingUri = streamingUri;
  }

  public String getName() {
    return name;
  }

  public URI getUri() {
    return uri;
  }

  public URI getStreamingUri() {
    return streamingUri;
  }

  private AtomicReference<Map<String, Bucket>> getCurrentBuckets() {
    if (currentBuckets == null) {
      throw new ConfigurationException("Buckets were never populated.");
    }
    return currentBuckets;
  }

  public Map<String, Bucket> getROBuckets() {
    return Collections.unmodifiableMap(currentBuckets.get());
  }

  public URI getBucketsUri() {
    return bucketsUri;
  }

  void setBucketsUri(URI newBucketsUri) {
    this.bucketsUri = newBucketsUri;
  }

  public void replaceBuckets(Map<String, Bucket> replacingMap) {
    HashMap<String, Bucket> swapMap
      = new HashMap<String, Bucket>(replacingMap);
    currentBuckets.set(swapMap);
  }

  public boolean hasBucket(String bucketName) {
    boolean bucketFound = false;
    if (getCurrentBuckets().get().containsKey(bucketName)) {
      bucketFound = true;
    }
    return bucketFound;
  }
}
