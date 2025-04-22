package com.couchbase.client.core.tracing;

import io.opentracing.ActiveSpan;
import io.opentracing.ActiveSpanSource;
import io.opentracing.Span;
import io.opentracing.SpanContext;

import java.util.Map;

public interface NoopActiveSpanSource extends ActiveSpanSource {
    NoopActiveSpanSource INSTANCE = new NoopActiveSpanSourceImpl();

    interface NoopActiveSpan extends ActiveSpan {
        NoopActiveSpanSource.NoopActiveSpan INSTANCE = new NoopActiveSpanSourceImpl.NoopActiveSpanImpl();
    }
    interface NoopContinuation extends ActiveSpan.Continuation {
        NoopActiveSpanSource.NoopContinuation INSTANCE = new NoopActiveSpanSourceImpl.NoopContinuationImpl();
    }
}

class NoopActiveSpanSourceImpl implements NoopActiveSpanSource {
    @Override
    public ActiveSpan makeActive(Span span) {
        return NoopActiveSpanSource.NoopActiveSpan.INSTANCE;
    }

    @Override
    public ActiveSpan activeSpan() { return null; }

    static class NoopActiveSpanImpl implements NoopActiveSpanSource.NoopActiveSpan {
        @Override
        public void deactivate() {}

        @Override
        public Continuation capture() {
            return NoopActiveSpanSource.NoopContinuation.INSTANCE;
        }

        @Override
        public SpanContext context() {
            return NoopSpanContextImpl.INSTANCE;
        }

        @Override
        public void close() {}

        @Override
        public NoopActiveSpan setTag(String key, String value) {
            return NoopActiveSpanSource.NoopActiveSpan.INSTANCE;
        }

        @Override
        public NoopActiveSpan setTag(String key, boolean value) {
            return NoopActiveSpanSource.NoopActiveSpan.INSTANCE;
        }

        @Override
        public NoopActiveSpan setTag(String key, Number value) {
            return NoopActiveSpanSource.NoopActiveSpan.INSTANCE;
        }

        @Override
        public NoopActiveSpan log(Map<String, ?> fields) {
            return NoopActiveSpanSource.NoopActiveSpan.INSTANCE;
        }

        @Override
        public NoopActiveSpan log(long timestampMicroseconds, Map<String, ?> fields) {
            return NoopActiveSpanSource.NoopActiveSpan.INSTANCE;
        }

        @Override
        public NoopActiveSpan log(String event) {
            return NoopActiveSpanSource.NoopActiveSpan.INSTANCE;
        }

        @Override
        public NoopActiveSpan log(long timestampMicroseconds, String event) {
            return NoopActiveSpanSource.NoopActiveSpan.INSTANCE;
        }

        @Override
        public NoopActiveSpan setBaggageItem(String key, String value) {
            return NoopActiveSpanSource.NoopActiveSpan.INSTANCE;
        }

        @Override
        public String getBaggageItem(String key) {
            return null;
        }

        @Override
        public NoopActiveSpan setOperationName(String operationName) {
            return NoopActiveSpanSource.NoopActiveSpan.INSTANCE;
        }

        @Override
        public NoopActiveSpan log(String eventName, Object payload) {
            return NoopActiveSpanSource.NoopActiveSpan.INSTANCE;
        }

        @Override
        public NoopActiveSpan log(long timestampMicroseconds, String eventName, Object payload) {
            return NoopActiveSpanSource.NoopActiveSpan.INSTANCE;
        }
    }

    static class NoopContinuationImpl implements NoopActiveSpanSource.NoopContinuation {
        @Override
        public ActiveSpan activate() {
            return NoopActiveSpanSource.NoopActiveSpan.INSTANCE;
        }
    }
}
