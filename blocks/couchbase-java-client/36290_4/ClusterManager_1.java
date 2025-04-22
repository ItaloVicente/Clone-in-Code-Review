package com.couchbase.client.java.util;

public class TestProperties {

  private static String seedNode;
  private static String bucket;
  private static String password;

  static {
    seedNode = System.getProperty("seedNode", "127.0.0.1");
    bucket = System.getProperty("bucket", "default");
    password = System.getProperty("password", "");
  }

  public static String seedNode() {
    return seedNode;
  }

  public static String bucket() {
    return bucket;
  }

  public static String password() {
    return password;
  }
}
