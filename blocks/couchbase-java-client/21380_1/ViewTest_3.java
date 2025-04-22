
package com.couchbase.client.protocol.views;

public class DesignDocument {

  private String name;

  public String getName() {
    return name;
  }

  public DesignDocument setName(String name) {
    this.name = name;
    return this;
  }

  public String toJson() {
    return "";
  }
}
