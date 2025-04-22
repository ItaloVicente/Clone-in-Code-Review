package com.couchbase.client.java;

import com.couchbase.client.java.document.Document;
import com.couchbase.client.java.document.JsonDocument;
import rx.Observable;

public interface Bucket {

  Observable<JsonDocument> get(String id);

  <D extends Document> Observable<D> get(String id, Class<D> target);

  <D extends Document> Observable<D> insert(D document);

  <D extends Document> Observable<D> upsert(D document);

}
