package com.couchbase.client.java.document;

public interface Document {

  String id();

  Object content();

  Document content(Object content);

  long cas();

  int expiry();

}
