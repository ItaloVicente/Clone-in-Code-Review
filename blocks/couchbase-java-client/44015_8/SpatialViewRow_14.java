package com.couchbase.client.java.view;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.document.json.JsonObject;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@InterfaceStability.Committed
@InterfaceAudience.Public
public interface SpatialViewResult extends Iterable<SpatialViewRow> {

    List<SpatialViewRow> allRows();

    List<SpatialViewRow> allRows(long timeout, TimeUnit timeUnit);

    Iterator<SpatialViewRow> rows();

    Iterator<SpatialViewRow> rows(long timeout, TimeUnit timeUnit);

    boolean success();

    JsonObject error();

    JsonObject debug();
}
