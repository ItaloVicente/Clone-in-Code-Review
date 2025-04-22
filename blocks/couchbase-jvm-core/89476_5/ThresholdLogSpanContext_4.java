
package com.couchbase.client.core.tracing;

import com.couchbase.client.core.logging.CouchbaseLogger;
import com.couchbase.client.core.logging.CouchbaseLoggerFactory;
import io.opentracing.References;
import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.Tracer;

import java.util.HashMap;
import java.util.Map;

public class ThresholdLogSpanBuilder implements Tracer.SpanBuilder {

    private static final CouchbaseLogger LOGGER = CouchbaseLoggerFactory.getInstance(ThresholdLogSpanBuilder.class);

    private final ThresholdLogTracer tracer;
    private String operationName;
    private boolean ignoreActiveSpan;
    private final Map<String, Object> tags;
    private ThresholdLogReference parentRef;
    private ThresholdLogReference followRef;
    private long startTimeMicroseconds;

    private final ThresholdLogScopeManager scopeManager;

    ThresholdLogSpanBuilder(final ThresholdLogTracer tracer, final String operationName,
        final ThresholdLogScopeManager scopeManager) {
        this.tracer = tracer;
        this.scopeManager = scopeManager;
        this.operationName = operationName;
        ignoreActiveSpan = false;
        tags = new HashMap<String, Object>();
        parentRef = null;
        followRef = null;
    }

    @Override
    public ThresholdLogSpanBuilder asChildOf(final SpanContext parent) {
        return addReference(References.CHILD_OF, parent);
    }

    @Override
    public ThresholdLogSpanBuilder asChildOf(final Span parent) {
        return addReference(References.CHILD_OF, parent != null ? parent.context() : null);
    }

    @Override
    public ThresholdLogSpanBuilder addReference(final String type, final SpanContext context) {
        if (!(context instanceof ThresholdLogSpanContext)) {
            LOGGER.debug("The referenced context must be a {}, but was {}", getClass().getName(),
                context.getClass().getName());
            return this;
        }

        if (type.equals(References.CHILD_OF)) {
            parentRef = ThresholdLogReference.childOf((ThresholdLogSpanContext) context);
        } else if (type.equals(References.FOLLOWS_FROM)) {
            followRef = ThresholdLogReference.followsFrom((ThresholdLogSpanContext) context);
        } else {
            LOGGER.debug("Only CHILD_OF and FOLLOWS_FROM are supported. Supplied: {}", type);
        }

        return this;
    }

    @Override
    public ThresholdLogSpanBuilder ignoreActiveSpan() {
        ignoreActiveSpan = true;
        return this;
    }

    @Override
    public ThresholdLogSpanBuilder withTag(final String key, final String value) {
        tags.put(key, value);
        return this;
    }

    @Override
    public ThresholdLogSpanBuilder withTag(final String key, final boolean value) {
        tags.put(key, value);
        return this;
    }

    @Override
    public ThresholdLogSpanBuilder withTag(final String key, final Number value) {
        tags.put(key, value);
        return this;
    }

    @Override
    public ThresholdLogSpanBuilder withStartTimestamp(final long microseconds) {
        this.startTimeMicroseconds = microseconds;
        return this;
    }

    @Override
    public Scope startActive(final boolean finishSpanOnClose) {
        return scopeManager.activate(start(), finishSpanOnClose);
    }

    @Override
    public Span startManual() {
        return start();
    }

    @Override
    public Span start() {
        ThresholdLogSpanContext context;

        if (parentRef == null && !ignoreActiveSpan && null != scopeManager.active()) {
            asChildOf(scopeManager.active().span());
        }

        if (parentRef != null) {
            context = parentRef.spanContext();
        } else if (followRef != null) {
            context = followRef.spanContext();
        } else {
            context = new ThresholdLogSpanContext();
        }

        return new ThresholdLogSpan(tracer, context, operationName, tags, startTimeMicroseconds);
    }
}
