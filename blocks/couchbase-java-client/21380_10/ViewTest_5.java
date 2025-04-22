
package com.couchbase.client.protocol.views;

public class ViewDesign {

  String name = null;

  String map = null;

  String reduce = null;

  public ViewDesign(String name, String map) {
    this(name, map, "");
  }

  public ViewDesign(String name, String map, String reduce) {
    this.name = name;
    this.map = map;
    this.reduce = reduce;
  }

  public String getName() {
    return name;
  }

  public String getMap() {
    return map;
  }

  public String getReduce() {
    return reduce;
  }

}
