
package com.couchbase.client.core.tracing;

import io.opentracing.SpanContext;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SlowOperationSpanContext implements SpanContext {

    private final Map<String, String> baggage;

    private volatile String remoteSocket;

    public SlowOperationSpanContext() {
        this.baggage = Collections.<String, String>emptyMap();
    }

    @Override
    public Iterable<Map.Entry<String, String>> baggageItems() {
        return new HashMap<String, String>(baggage).entrySet();
    }

    public String baggageItem(String item) {
        return baggage.get(item);
    }

    public void storeBaggageItem(String item, String value) {
        this.baggage.put(item, value);
    }

    public String remoteSocket() {
        return remoteSocket;
    }

    public SlowOperationSpanContext remoteSocket(String remoteSocket) {
        this.remoteSocket = remoteSocket;
        return this;
    }
}
