package com.couchbase.client;

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
