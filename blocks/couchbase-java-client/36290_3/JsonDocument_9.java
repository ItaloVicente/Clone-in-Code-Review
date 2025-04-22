package com.couchbase.client.java.document;

public interface Document<T> {

  String id();

  Document<T> id(String id);

  T content();

  Document<T> content(T content);

  long cas();

  Document<T> cas(long cas);

  int expiry();

  Document<T> expiry(int expiry);

}
