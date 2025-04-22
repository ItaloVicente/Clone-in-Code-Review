
package com.couchbase.client.core.tracing;

import io.opentracing.SpanContext;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SlowlogSpanContext implements SpanContext {

    private final Map<String, String> baggage;

    public SlowlogSpanContext() {
        this.baggage = Collections.<String, String>emptyMap();
    }

    @Override
    public Iterable<Map.Entry<String, String>> baggageItems() {
        return new HashMap<String, String>(baggage).entrySet();
    }
}
