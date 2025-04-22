
package com.couchbase.client;

public final class CbTestConfig {
  public static final String CLUSTER_PASS_PROP = "cluster.password";
  public static final String CLUSTER_ADMINNAME_PROP = "cluster.adminname";
  public static final String CLUSTER_PASS =
    System.getProperty(CLUSTER_PASS_PROP, "password");
  public static final String CLUSTER_ADMINNAME =
    System.getProperty(CLUSTER_ADMINNAME_PROP, "Administrator");

  private CbTestConfig() {
  }

}
