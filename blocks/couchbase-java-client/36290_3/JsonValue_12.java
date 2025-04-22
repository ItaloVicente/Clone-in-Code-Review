package com.couchbase.client.java.document.json;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JsonObject implements JsonValue {

  private final Map<String, Object> content;

  private JsonObject() {
    content = new HashMap<String, Object>();
  }

  public static JsonObject empty() {
    return new JsonObject();
  }

  public Object get(String name) {
    return content.get(name);
  }

  public JsonObject put(String name, String value) {
    content.put(name, value);
    return this;
  }

  public String getString(String name) {
    return (String) content.get(name);
  }

  public JsonObject put(String name, int value) {
    content.put(name, value);
    return this;
  }

  public int getInt(String name) {
    return (Integer) content.get(name);
  }

  public JsonObject put(String name, long value) {
    content.put(name, value);
    return this;
  }

  public long getLong(String name) {
    return (Long) content.get(name);
  }

  public JsonObject put(String name, double value) {
    content.put(name, value);
    return this;
  }

  public double getDouble(String name) {
    return (Double) content.get(name);
  }

  public JsonObject put(String name, boolean value) {
    content.put(name, value);
    return this;
  }

  public boolean getBoolean(String name) {
    return (Boolean) content.get(name);
  }

  public JsonObject put(String name, JsonObject value) {
    content.put(name, value);
    return this;
  }

  public JsonObject getObject(String name) {
    return (JsonObject) content.get(name);
  }

  public JsonObject put(String name, JsonArray value) {
    content.put(name, value);
    return this;
  }

  public JsonArray getArray(String name) {
    return (JsonArray) content.get(name);
  }

  public Set<String> getNames() {
    return content.keySet();
  }

  public boolean isEmpty() {
    return content.isEmpty();
  }

  public Map<String, Object> toMap() {
    return content;
  }

  public int size() {
    return content.size();
  }

}
