
package com.couchbase.client.clustermanager;

public enum BucketType {

  MEMCACHED("memcached"),

  COUCHBASE("membase");

  private String type;

  BucketType(String type) {
    this.type = type;
  }

  public String getBucketType() {
    return type;
  }
}
