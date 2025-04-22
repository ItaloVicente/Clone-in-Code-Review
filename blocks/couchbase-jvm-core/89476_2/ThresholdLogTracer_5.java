
package com.couchbase.client.core.tracing;

import io.opentracing.SpanContext;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ThresholdLogSpanContext implements SpanContext {

    private final Map<String, String> baggageItems;

    public ThresholdLogSpanContext() {
        this.baggageItems = new ConcurrentHashMap<String, String>();
    }

    @Override
    public Iterable<Map.Entry<String, String>> baggageItems() {
        return new HashMap<String, String>(baggageItems).entrySet();
    }

    public ThresholdLogSpanContext baggageItem(final String item, final String value) {
        baggageItems.put(item, value);
        return this;
    }

    public String baggageItem(final String item) {
        return baggageItems.get(item);
    }

}
