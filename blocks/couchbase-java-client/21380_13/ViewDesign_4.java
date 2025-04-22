
package com.couchbase.client.protocol.views;

public class SpatialViewDesign {

  String name = null;

  String map = null;

  public SpatialViewDesign(String name, String map) {
    this.name = name;
    this.map = map;
  }

  public String getName() {
    return name;
  }

  public String getMap() {
    return map;
  }

}
