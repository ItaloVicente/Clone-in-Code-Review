package com.couchbase.client.java.view;

import com.couchbase.client.core.BackpressureException;
import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.document.Document;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.error.TranscodingException;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@InterfaceStability.Committed
@InterfaceAudience.Public
public interface SpatialViewRow {

    String id();

    JsonArray key();

    Object value();


    JsonObject geometry();

    JsonDocument document();

    JsonDocument document(long timeout, TimeUnit timeUnit);

    <D extends Document<?>> D document(final Class<D> target);

    <D extends Document<?>> D document(final Class<D> target, long timeout, TimeUnit timeUnit);

}
