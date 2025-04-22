
package com.couchbase.client.core.tracing;

import io.opentracing.References;

public class ThresholdLogReference {

    private final ThresholdLogSpanContext spanContext;
    private final String type;

    public static ThresholdLogReference childOf(final ThresholdLogSpanContext spanContext) {
        return new ThresholdLogReference(spanContext, References.CHILD_OF);
    }

    public static ThresholdLogReference followsFrom(final ThresholdLogSpanContext spanContext) {
        return new ThresholdLogReference(spanContext, References.FOLLOWS_FROM);
    }

    private ThresholdLogReference(final ThresholdLogSpanContext spanContext, final String type) {
        this.spanContext = spanContext;
        this.type = type;
    }

    public ThresholdLogSpanContext spanContext() {
        return spanContext;
    }

    public String type() {
        return type;
    }

}
