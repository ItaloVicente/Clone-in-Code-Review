
package com.couchbase.client.protocol.views;

public class SpatialViewRowNoDocs implements ViewRow {
  private final String id;
  private final String bbox;
  private final String geometry;
  private final String value;

  public SpatialViewRowNoDocs(String id, String bbox, String geometry,
    String value) {
    this.id = parseField(id);
    this.bbox = parseField(bbox);
    this.geometry = parseField(geometry);
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
  public String getBbox() {
    return bbox;
  }

  @Override
  public String getGeometry() {
    return geometry;
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

  @Override
  public String getKey() {
     throw new UnsupportedOperationException("Spatial views don't contain "
       + "a key");
  }

}
