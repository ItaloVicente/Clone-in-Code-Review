
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

public class SlowlogSpanBuilder implements Tracer.SpanBuilder {

    private final ScopeManager scopeManager;
    private final String operationName;
    private long startTimeMicroseconds;
    private List<SlowlogReference> references = Collections.emptyList();
    private final Map<String, Object> tags = new HashMap<String, Object>();
    private boolean ignoreActiveSpan = false;

    public SlowlogSpanBuilder(String operationName, ScopeManager scopeManager) {
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
        if (!(referencedContext instanceof SlowlogSpanContext)) {
            return this;
        }

        if (!References.CHILD_OF.equals(referenceType)
            && !References.FOLLOWS_FROM.equals(referenceType)) {
            return this;
        }

        if (references.isEmpty()) {
            references = Collections.singletonList(new SlowlogReference(referencedContext, referenceType));
        } else {
            if (references.size() == 1) {
                references = new ArrayList<SlowlogReference>(references);
            }
            references.add(new SlowlogReference(referencedContext, referenceType));
        }

        return this;
    }


    @Override
    public Span start() {
        return null;
    }
}
