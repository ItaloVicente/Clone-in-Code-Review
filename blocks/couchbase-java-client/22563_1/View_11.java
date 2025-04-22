
package com.couchbase.client.protocol.views;

public class SpatialViewRowWithDocs implements ViewRow {
  private final String id;
  private final String bbox;
  private final String geometry;
  private final Object doc;

  public SpatialViewRowWithDocs(String id, String bbox, String geometry,
    Object doc) {
    this.id = parseField(id);
    this.bbox = parseField(bbox);
    this.geometry = parseField(geometry);
    this.doc = doc;
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
  public String getBbox() {
    return bbox;
  }

  @Override
  public String getGeometry() {
    return geometry;
  }

  @Override
  public Object getDocument() {
    return doc;
  }

  @Override
  public String getKey() {
     throw new UnsupportedOperationException("Spatial views don't contain "
       + "a key");
  }

  @Override
  public String getValue() {
     throw new UnsupportedOperationException("Spatial views don't contain "
       + "a value");
  }

}
