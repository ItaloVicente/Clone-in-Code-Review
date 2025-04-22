package com.couchbase.client.core.tracing;

import io.opentracing.SpanContext;

import java.util.Collections;
import java.util.Map;


public interface NoopSpanContext extends SpanContext {
}

final class NoopSpanContextImpl implements NoopSpanContext {
    static final NoopSpanContextImpl INSTANCE = new NoopSpanContextImpl();

    @Override
    public Iterable<Map.Entry<String, String>> baggageItems() {
        return Collections.emptyList();
    }

    @Override
    public String toString() { return NoopSpanContext.class.getSimpleName(); }

}
