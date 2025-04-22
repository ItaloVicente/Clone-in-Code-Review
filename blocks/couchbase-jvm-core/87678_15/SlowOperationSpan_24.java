
package com.couchbase.client.core.tracing;

import io.opentracing.Scope;
import io.opentracing.ScopeManager;
import io.opentracing.Span;

public class SlowOperationScopeManager implements ScopeManager {

    final ThreadLocal<SlowOperationScope> tlsScope = new ThreadLocal<SlowOperationScope>();

    @Override
    public Scope activate(Span span, boolean finishOnClose) {
        return new SlowOperationScope(this, span, finishOnClose);
    }

    @Override
    public Scope active() {
        return tlsScope.get();
    }

}
