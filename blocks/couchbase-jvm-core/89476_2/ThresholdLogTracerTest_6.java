
package com.couchbase.client.core.tracing;

import io.opentracing.Scope;
import io.opentracing.ScopeManager;
import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.Tracer;
import io.opentracing.propagation.Format;

import java.util.concurrent.TimeUnit;

public class ThresholdLogTracer implements Tracer {

    private final ThresholdLogScopeManager scopeManager;

    ThresholdLogTracer() {
        this.scopeManager = new ThresholdLogScopeManager();
    }

    @Override
    public ScopeManager scopeManager() {
        return scopeManager;
    }

    @Override
    public Span activeSpan() {
        Scope scope = scopeManager.active();
        return scope == null ? null : scope.span();
    }

    @Override
    public SpanBuilder buildSpan(String operationName) {
        return new ThresholdLogSpanBuilder(this, operationName, scopeManager);
    }

    @Override
    public <C> void inject(SpanContext spanContext, Format<C> format, C carrier) {
        throw new UnsupportedOperationException("Not supported by the " + getClass().getSimpleName());
    }

    @Override
    public <C> SpanContext extract(Format<C> format, C carrier) {
        throw new UnsupportedOperationException("Not supported by the " + getClass().getSimpleName());
    }

    public void reportSpan(final Span span) {
    }

    public static long currentTimeMicros() {
        return TimeUnit.NANOSECONDS.toMicros(System.nanoTime());
    }

}
