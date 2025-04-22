package com.couchbase.client.java.view;

import com.couchbase.client.java.document.json.JsonObject;
import rx.Observable;

public interface AsyncSpatialViewResult {

    Observable<AsyncSpatialViewRow> rows();

    boolean success();

    JsonObject error();

    JsonObject debug();
}
