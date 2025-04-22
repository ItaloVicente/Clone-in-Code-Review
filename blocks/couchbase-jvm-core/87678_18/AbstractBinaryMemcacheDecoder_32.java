
package com.couchbase.client.core.tracing;

import io.opentracing.ScopeManager;
import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.Tracer;
import io.opentracing.propagation.Format;

public class SlowOperationTracer implements Tracer {

    private final ScopeManager scopeManager;
    private final SlowOperationReporter reporter;

    public SlowOperationTracer() {
        this(SlowOperationReporter.createWithDefaults());
    }

    public SlowOperationTracer(final SlowOperationReporter reporter) {
        this.scopeManager = new SlowOperationScopeManager();
        this.reporter = reporter;
    }

    @Override
    public ScopeManager scopeManager() {
        return scopeManager;
    }

    @Override
    public SpanBuilder buildSpan(final String operationName) {
        return new SlowOperationSpanBuilder(this, operationName, scopeManager);
    }

    @Override
    public Span activeSpan() {
        return null;
    }

    @Override
    public <C> void inject(final SpanContext spanContext, final Format<C> format, final C carrier) {

    }

    @Override
    public <C> SpanContext extract(final Format<C> format, final C carrier) {
        return null;
    }

    public void reportSpan(final SlowOperationSpan span) {
        reporter.report(span);
    }

    public SlowOperationReporter reporter() {
        return reporter;
    }
}
