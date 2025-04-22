
package com.couchbase.client.core.tracing;

import io.opentracing.SpanContext;

public class SlowlogReference {

    private final SpanContext spanContext;
    private final String type;

    public SlowlogReference(SpanContext spanContext, String type) {
        this.spanContext = spanContext;
        this.type = type;
    }

    public SpanContext spanContext() {
        return spanContext;
    }

    public String type() {
        return type;
    }
}
