
package com.couchbase.client.core.tracing;

import io.opentracing.Scope;
import io.opentracing.ScopeManager;
import io.opentracing.Span;

public class SlowlogScopeManager implements ScopeManager {

    final ThreadLocal<SlowlogScope> tlsScope = new ThreadLocal<SlowlogScope>();

    @Override
    public Scope activate(Span span, boolean finishOnClose) {
        return new SlowlogScope(this, span, finishOnClose);
    }

    @Override
    public Scope active() {
        return tlsScope.get();
    }

}
