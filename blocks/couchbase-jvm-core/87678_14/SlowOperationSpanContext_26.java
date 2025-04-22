
package com.couchbase.client.core.tracing;

import io.opentracing.References;
import io.opentracing.Scope;
import io.opentracing.ScopeManager;
import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.Tracer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SlowOperationSpanBuilder implements Tracer.SpanBuilder {

    private final ScopeManager scopeManager;
    private final String operationName;
    private long startTimeMicroseconds;
    private final SlowOperationTracer tracer;

    private List<SlowOperationReference> references = Collections.emptyList();
    private final Map<String, Object> tags = new HashMap<String, Object>();
    private boolean ignoreActiveSpan = false;

    public SlowOperationSpanBuilder(SlowOperationTracer tracer, String operationName, ScopeManager scopeManager) {
        this.tracer = tracer;
        this.operationName = operationName;
        this.scopeManager = scopeManager;
    }

    @Override
    public Tracer.SpanBuilder asChildOf(SpanContext parent) {
        return addReference(References.CHILD_OF, parent);
    }

    @Override
    public Tracer.SpanBuilder asChildOf(Span parent) {
        return addReference(References.CHILD_OF, parent != null ? parent.context() : null);
    }

    @Override
    public Tracer.SpanBuilder withTag(String key, String value) {
        tags.put(key, value);
        return this;
    }

    @Override
    public Tracer.SpanBuilder withTag(String key, boolean value) {
        tags.put(key, value);
        return this;
    }

    @Override
    public Tracer.SpanBuilder withTag(String key, Number value) {
        tags.put(key, value);
        return this;
    }

    @Override
    public Tracer.SpanBuilder withStartTimestamp(long microseconds) {
        this.startTimeMicroseconds = microseconds;
        return this;
    }

    @Override
    public Scope startActive(boolean finishSpanOnClose) {
        return scopeManager.activate(start(), finishSpanOnClose);
    }

    @Override
    public Span startManual() {
        return start();
    }

    @Override
    public Tracer.SpanBuilder ignoreActiveSpan() {
        ignoreActiveSpan = true;
        return this;
    }

    @Override
    public Tracer.SpanBuilder addReference(final String referenceType, final SpanContext referencedContext) {
        if (!(referencedContext instanceof SlowOperationSpanContext)) {
            return this;
        }

        if (!References.CHILD_OF.equals(referenceType)
            && !References.FOLLOWS_FROM.equals(referenceType)) {
            return this;
        }

        if (references.isEmpty()) {
            references = Collections.singletonList(new SlowOperationReference((SlowOperationSpanContext) referencedContext, referenceType));
        } else {
            if (references.size() == 1) {
                references = new ArrayList<SlowOperationReference>(references);
            }
            references.add(new SlowOperationReference((SlowOperationSpanContext) referencedContext, referenceType));
        }

        return this;
    }

    private SlowOperationSpanContext preferredReference() {
        SlowOperationReference preferredReference = references.get(0);
        for (SlowOperationReference reference: references) {
            if (References.CHILD_OF.equals(reference.type())
                && !References.CHILD_OF.equals(preferredReference.type())) {
                preferredReference = reference;
                break;
            }
        }
        return preferredReference.spanContext();
    }

    @Override
    public Span start() {
        SlowOperationSpanContext context;

        if (references.isEmpty() && !ignoreActiveSpan && null != scopeManager.active()) {
            asChildOf(scopeManager.active().span());
        }

        if (references.isEmpty()) {
            context = new SlowOperationSpanContext();
        } else {
            context = preferredReference();
        }

        if (startTimeMicroseconds == 0) {
            startTimeMicroseconds = TimeUnit.NANOSECONDS.toMicros(System.nanoTime());
        }
        Span span = new SlowOperationSpan(
            tracer,
            context,
            tags,
            operationName,
            startTimeMicroseconds
        );
        return span;
    }
}
