package com.couchbase.client.core.tracing;

import io.opentracing.ActiveSpan;
import io.opentracing.BaseSpan;
import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.Tracer;

import java.util.Collections;
import java.util.Map;

public interface NoopSpanBuilder extends Tracer.SpanBuilder, NoopSpanContext {
    static final NoopSpanBuilder INSTANCE = new NoopSpanBuilderImpl();
}

final class NoopSpanBuilderImpl implements NoopSpanBuilder {

    @Override
    public Tracer.SpanBuilder addReference(String refType, SpanContext referenced) {
        return this;
    }

    @Override
    public Tracer.SpanBuilder asChildOf(SpanContext parent) {
        return this;
    }

    @Override
    public Tracer.SpanBuilder ignoreActiveSpan() { return this; }

    @Override
    public Tracer.SpanBuilder asChildOf(BaseSpan parent) {
        return this;
    }

    @Override
    public Tracer.SpanBuilder withTag(String key, String value) {
        return this;
    }

    @Override
    public Tracer.SpanBuilder withTag(String key, boolean value) {
        return this;
    }

    @Override
    public Tracer.SpanBuilder withTag(String key, Number value) {
        return this;
    }

    @Override
    public Tracer.SpanBuilder withStartTimestamp(long microseconds) {
        return this;
    }

    @Override
    public Span start() {
        return startManual();
    }

    @Override
    public ActiveSpan startActive() {
        return NoopActiveSpanSource.NoopActiveSpan.INSTANCE;
    }

    @Override
    public Span startManual() {
        return NoopSpanImpl.INSTANCE;
    }

    @Override
    public Iterable<Map.Entry<String, String>> baggageItems() {
        return Collections.EMPTY_MAP.entrySet();
    }

    @Override
    public String toString() { return NoopSpanBuilder.class.getSimpleName(); }
}
