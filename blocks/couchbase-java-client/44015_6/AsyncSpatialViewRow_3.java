package com.couchbase.client.java.view;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.document.json.JsonObject;
import rx.Observable;

@InterfaceStability.Committed
@InterfaceAudience.Public
public interface AsyncSpatialViewResult {

    Observable<AsyncSpatialViewRow> rows();

    boolean success();

    JsonObject error();

    JsonObject debug();
}
