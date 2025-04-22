
package com.couchbase.client.protocol.views;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class ViewDesign {

  String name;
  String map;
  String reduce;

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

  public String toJson() {
    JSONObject jsonView = new JSONObject();
    try {
      jsonView.append("map", map);
      if(!reduce.isEmpty()) {
        jsonView.append("reduce", reduce);
      }
    } catch (JSONException ex) {
      throw new RuntimeException("Could not compose View object: ", ex);
    }

    return jsonView.toString();
  }

}
