
package com.couchbase.client.protocol.views;

public class ViewRowNoDocs implements ViewRow {
  private final String id;
  private final String key;
  private final String value;

  public ViewRowNoDocs(String id, String key, String value) {
    this.id = parseField(id);
    this.key = parseField(key);
    this.value = parseField(value);
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
    throw new UnsupportedOperationException("This view result doesn't contain "
        + "documents");
  }
}
