
package com.couchbase.client.protocol.views;

public class ViewRowWithDocs implements ViewRow {
  private final String id;
  private final String key;
  private final String value;
  private final Object doc;

  public ViewRowWithDocs(String id, String key, String value, Object doc) {
    this.id = parseField(id);
    this.key = parseField(key);
    this.value = parseField(value);
    this.doc = parseField((String)doc);
  }

  private String parseField(String field) {
    if (field != null && field.equals("null")) {
      return null;
    } else {
      return field;
    }
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public String getKey() {
    return key;
  }

  @Override
  public String getValue() {
    return value;
  }

  @Override
  public Object getDocument() {
    return doc;
  }
}
