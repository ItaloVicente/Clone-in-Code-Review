package com.couchbase.client.core.tracing;

import io.opentracing.ActiveSpan;
import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.Tracer;
import io.opentracing.propagation.Format;

public interface NoopTracer extends Tracer {
}

final class NoopTracerImpl implements NoopTracer {
    final static NoopTracer INSTANCE = new NoopTracerImpl();

    @Override
    public SpanBuilder buildSpan(String operationName) { return NoopSpanBuilderImpl.INSTANCE; }

    @Override
    public <C> void inject(SpanContext spanContext, Format<C> format, C carrier) {}

    @Override
    public <C> SpanContext extract(Format<C> format, C carrier) { return NoopSpanBuilderImpl.INSTANCE; }

    @Override
    public String toString() { return NoopTracer.class.getSimpleName(); }

    @Override
    public ActiveSpan activeSpan() {
        return null;
    }

    @Override
    public ActiveSpan makeActive(Span span) {
        return NoopActiveSpanSource.NoopActiveSpan.INSTANCE;
    }
}

