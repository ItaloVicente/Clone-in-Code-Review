
package com.couchbase.client.core.tracing;

import io.opentracing.Span;
import io.opentracing.SpanContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class SlowlogSpan implements Span {

    private final SlowlogTracer tracer;
    private final SlowlogSpanContext spanContext;
    private final Map<String, Object> tags;
    private List<SlowlogLogData> logs;
    private String operationName;
    private AtomicBoolean finished;
    private long durationMicroseconds;
    private final long startTimeMicroseconds;

    public SlowlogSpan(SlowlogTracer tracer, SlowlogSpanContext spanContext, Map<String, Object> tags,
                       String operationName, long startTimeMicroseconds) {
        this.spanContext = spanContext;
        this.tags = tags;
        this.operationName = operationName;
        this.tracer = tracer;
        this.finished = new AtomicBoolean(false);
        this.startTimeMicroseconds = startTimeMicroseconds;
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
            spanContext.storeBaggageItem(key, value);
            return this;
        }
    }

    @Override
    public synchronized String getBaggageItem(String key) {
        return spanContext.baggageItem(key);
    }

    @Override
    public synchronized Span setOperationName(String operationName) {
        this.operationName = operationName;
        return this;
    }

    public synchronized String getOperationName() {
        return operationName;
    }

    @Override
    public void finish() {
        finish(currentMicros());
    }

    @Override
    public void finish(long finishMicros) {
        if (finished.compareAndSet(false, true)) {
            durationMicroseconds = finishMicros - startTimeMicroseconds;
        } else {
            return;
        }
        tracer.reportSpan(this);
    }

    public long duration() {
        return durationMicroseconds;
    }

    private static long currentMicros() {
        return TimeUnit.NANOSECONDS.toMicros(System.nanoTime());
    }

    @Override
    public String toString() {
        return "SlowlogSpan{" +
            "tracer=" + tracer +
            ", spanContext=" + spanContext +
            ", tags=" + tags +
            ", logs=" + logs +
            ", operationName='" + operationName + '\'' +
            ", finished=" + finished +
            ", durationMicroseconds=" + durationMicroseconds +
            ", startTimeMicroseconds=" + startTimeMicroseconds +
            '}';
    }
}
