
package com.couchbase.client.protocol.views;

public abstract class AbstractView {
  private final String viewName;
  private final String designDocumentName;
  private final String databaseName;

  public AbstractView(String dn, String ddn, String vn) {
    databaseName = dn;
    designDocumentName = ddn;
    viewName = vn;
  }

  public String getDatabaseName() {
    return databaseName;
  }

  public String getDesignDocumentName() {
    return designDocumentName;
  }

  public String getViewName() {
    return viewName;
  }

  public abstract boolean hasMap();

  public abstract boolean hasReduce();

  public abstract String getURI();
}
