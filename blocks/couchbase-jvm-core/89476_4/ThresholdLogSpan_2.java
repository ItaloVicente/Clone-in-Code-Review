
package com.couchbase.client.core.tracing;

import io.opentracing.Scope;
import io.opentracing.ScopeManager;
import io.opentracing.Span;

public class ThresholdLogScopeManager implements ScopeManager {

    final ThreadLocal<ThresholdLogScope> scope = new ThreadLocal<ThresholdLogScope>();

    @Override
    public Scope activate(Span span, boolean finishSpanOnClose) {
        return new ThresholdLogScope(this, span, finishSpanOnClose);
    }

    @Override
    public Scope active() {
        return scope.get();
    }

}
