
package com.couchbase.client.protocol.views;

public interface ViewRow {
  String getId();

  String getKey();

  String getValue();

  Object getDocument();
}
