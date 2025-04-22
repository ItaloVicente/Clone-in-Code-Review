
package com.couchbase.client.protocol.views;

public class ViewRowReduced implements ViewRow {
  private String key;
  private String value;

  public ViewRowReduced(String key, String value) {
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
    throw new UnsupportedOperationException("Reduced views don't contain "
        + "document ids");
  }

  @Override
  public String getKey() {
    return key;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String getDocument() {
    throw new UnsupportedOperationException("Reduced views don't contain "
        + "documents");
  }
}
