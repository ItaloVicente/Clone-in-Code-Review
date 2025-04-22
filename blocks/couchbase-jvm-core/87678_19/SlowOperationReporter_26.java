
package com.couchbase.client.core.tracing;

public class SlowOperationReference {

    private final SlowOperationSpanContext spanContext;
    private final String type;

    public SlowOperationReference(SlowOperationSpanContext spanContext, String type) {
        this.spanContext = spanContext;
        this.type = type;
    }

    public SlowOperationSpanContext spanContext() {
        return spanContext;
    }

    public String type() {
        return type;
    }
}
