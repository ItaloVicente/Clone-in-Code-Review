
package com.couchbase.client.core.tracing;

import io.opentracing.Span;
import io.opentracing.SpanContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SlowlogSpan implements Span {

    private final SlowlogTracer tracer;
    private final SlowlogSpanContext spanContext;
    private final Map<String, Object> tags;
    private List<SlowlogLogData> logs;
    private String operationName;

    public SlowlogSpan(SlowlogTracer tracer, SlowlogSpanContext spanContext, Map<String, Object> tags, String operationName) {
        this.spanContext = spanContext;
        this.tags = tags;
        this.operationName = operationName;
        this.tracer = tracer;
    }

    @Override
    public SpanContext context() {
        return spanContext;
    }

    @Override
    public synchronized Span setTag(String key, String value) {
        this.tags.put(key, value);
        return this;
    }

    @Override
    public synchronized Span setTag(String key, boolean value) {
        this.tags.put(key, value);
        return this;    }

    @Override
    public synchronized Span setTag(String key, Number value) {
        this.tags.put(key, value);
        return this;
    }

    @Override
    public Span log(Map<String, ?> fields) {
        return log(currentMicros(), fields);
    }

    @Override
    public  Span log(long timestampMicroseconds, Map<String, ?> fields) {
        if (fields == null) {
            return this;
        }
        synchronized (this) {
            if (logs == null) {
                this.logs = new ArrayList<SlowlogLogData>();
            }
            logs.add(new SlowlogLogData(timestampMicroseconds, null, fields));
        }
        return this;
    }

    @Override
    public Span log(String event) {
        return log(currentMicros(), event);
    }

    @Override
    public Span log(long timestampMicroseconds, String event) {
        if (event == null) {
            return this;
        }
        synchronized (this) {
            if (logs == null) {
                this.logs = new ArrayList<SlowlogLogData>();
            }
            logs.add(new SlowlogLogData(timestampMicroseconds, event, null));
        }
        return this;
    }

    @Override
    public Span setBaggageItem(String key, String value) {
        if (key == null || value == null) {
            return this;
        }
        synchronized (this) {
            tracer.setBaggage(this, key, value);
            return this;
        }
    }

    @Override
    public String getBaggageItem(String key) {
        synchronized (this) {
            return spanContext.getBaggageItem(key);
        }
    }

    @Override
    public Span setOperationName(String operationName) {
        synchronized (this) {
            this.operationName = operationName;
        }
        return this;
    }

    @Override
    public void finish() {

    }

    @Override
    public void finish(long finishMicros) {

    }

    private static long currentMicros() {
        return TimeUnit.NANOSECONDS.toMicros(System.nanoTime());
    }
}
