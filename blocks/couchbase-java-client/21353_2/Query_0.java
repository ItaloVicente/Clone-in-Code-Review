
package com.couchbase.client.protocol.views;

import java.util.Arrays;
import java.util.List;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

public final class ComplexKey {

  private final List<Object> components;

  private static final Object EMPTY_OBJECT = new Object();

  private static final Object[] EMPTY_ARRAY = new Object[0];

  private ComplexKey(Object[] components) {
    this.components = Arrays.asList(components);
  }

  public static ComplexKey of(Object... components) {
    return new ComplexKey(components);
  }

  public static Object emptyObject() {
    return EMPTY_OBJECT;
  }

  public static Object[] emptyArray() {
    return EMPTY_ARRAY;
  }


  public String toJson() {
    if(components.size() == 1) {
      if(components.get(0) == EMPTY_OBJECT) {
        return new JSONObject().toString();
      } else {
        return components.get(0).toString();
      }
    }

    JSONArray key = new JSONArray();
    for (Object component : components) {
      if (component == EMPTY_OBJECT) {
        key.put(new JSONObject());
      } else {
        key.put(component);
      }
    }

    return key.toString();
  }

}
