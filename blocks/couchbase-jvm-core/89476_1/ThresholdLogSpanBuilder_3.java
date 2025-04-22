
package com.couchbase.client.core.tracing;

import com.couchbase.client.core.logging.CouchbaseLogger;
import com.couchbase.client.core.logging.CouchbaseLoggerFactory;
import io.opentracing.Span;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ThresholdLogSpan implements Span {

    private static final CouchbaseLogger LOGGER = CouchbaseLoggerFactory.getInstance(ThresholdLogSpan.class);

    private final ThresholdLogTracer tracer;
    private final Map<String, Object> tags;
    private final ThresholdLogSpanContext context;
    private final long startTimeMicroseconds;
    private long endTimeMicroseconds;
    private String operationName;
    private boolean finished;

    ThresholdLogSpan(final ThresholdLogTracer tracer, final ThresholdLogSpanContext context,
        final String operationName, final Map<String, Object> tags, final long startTimeMicroseconds) {
        this.context = context;
        this.tracer = tracer;
        this.operationName = operationName;
        this.tags = tags;
        this.startTimeMicroseconds = startTimeMicroseconds;
        finished = false;
    }

    @Override
    public ThresholdLogSpanContext context() {
        return context;
    }

    @Override
    public synchronized ThresholdLogSpan setTag(String key, String value) {
        tags.put(key, value);
        return this;
    }

    @Override
    public synchronized ThresholdLogSpan setTag(String key, boolean value) {
        tags.put(key, value);
        return this;
    }

    @Override
    public synchronized ThresholdLogSpan setTag(String key, Number value) {
        tags.put(key, value);
        return this;
    }

    @Override
    public ThresholdLogSpan log(Map<String, ?> fields) {
        return this;
    }

    @Override
    public ThresholdLogSpan log(String event) {
        return this;
    }

    @Override
    public ThresholdLogSpan log(long timestampMicroseconds, Map<String, ?> fields) {
        return this;
    }

    @Override
    public ThresholdLogSpan log(long timestampMicroseconds, String event) {
        return this;
    }

    @Override
    public ThresholdLogSpan setBaggageItem(String key, String value) {
        context.baggageItem(key, value);
        return this;
    }

    @Override
    public String getBaggageItem(String key) {
        return context.baggageItem(key);
    }

    @Override
    public synchronized ThresholdLogSpan setOperationName(String operationName) {
        this.operationName = operationName;
        return this;
    }

    @Override
    public void finish() {
        finish(ThresholdLogTracer.currentTimeMicros());
    }

    public synchronized long durationMicros() {
        return endTimeMicroseconds - startTimeMicroseconds;
    }

    public synchronized String operationName() {
        return operationName;
    }

    @Override
    public void finish(long finishMicros) {
        synchronized (this) {
            if (finished) {
                LOGGER.warn("Span has already been finished; will not be reported again.");
                return;
            }
            endTimeMicroseconds = finishMicros;
            finished = true;
        }
        tracer.reportSpan(this);
    }
}
