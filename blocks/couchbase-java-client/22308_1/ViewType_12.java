
package com.couchbase.client.protocol.views;

public class ViewRowWithDocsSpatial implements ViewRow {
  private final String id;
  private final String bbox;
  private final String geometry;
  private final Object doc;

  public ViewRowWithDocsSpatial(String id, String bbox, String geometry,
    Object doc) {
    this.id = parseField(id);
    this.bbox = parseField(bbox);
    this.geometry = parseField(geometry);
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
    throw new UnsupportedOperationException("Key is not supported on spatial"
      + " view rows.");
  }

  public String getBbox() {
    return bbox;
  }

  public String getGeometry() {
    return geometry;
  }

  @Override
  public Object getDocument() {
    return doc;
  }
}
