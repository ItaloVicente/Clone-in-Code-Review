
package com.couchbase.client.protocol.views;

public class SpatialView extends AbstractView {

  public SpatialView(String dn, String ddn, String vn) {
    super(dn, ddn, vn);
  }

  @Override
  public boolean hasMap() {
    return true;
  }

  @Override
  public boolean hasReduce() {
    return false;
  }

  @Override
  public String getURI() {
    return "/" + getDatabaseName() + "/_design/" + getDesignDocumentName()
      + "/_spatial/" + getViewName();
  }
}
