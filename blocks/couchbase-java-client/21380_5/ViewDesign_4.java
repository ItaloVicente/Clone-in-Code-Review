
package com.couchbase.client.protocol.views;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class SpatialViewDesign {

  String name;
  String map;

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
