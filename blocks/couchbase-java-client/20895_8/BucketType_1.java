
package com.couchbase.client.clustermanager;

public enum AuthType {

  NONE("none"),

  SASL("sasl");

  private String auth;

  AuthType(String auth) {
    this.auth = auth;
  }

  public String getAuthType() {
    return auth;
  }
}
