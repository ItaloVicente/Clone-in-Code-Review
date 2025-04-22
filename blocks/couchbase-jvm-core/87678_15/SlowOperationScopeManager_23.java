
package com.couchbase.client.core.tracing;

import io.opentracing.Scope;
import io.opentracing.Span;

public class SlowOperationScope implements Scope {

    private final SlowOperationScopeManager scopeManager;
    private final Span wrapped;
    private final boolean finishOnClose;
    private final SlowOperationScope toRestore;

    SlowOperationScope(final SlowOperationScopeManager scopeManager, final Span wrapped, final boolean finishOnClose) {
        this.scopeManager = scopeManager;
        this.wrapped = wrapped;
        this.finishOnClose = finishOnClose;
        this.toRestore = scopeManager.tlsScope.get();
        scopeManager.tlsScope.set(this);
    }

    @Override
    public void close() {
        if (scopeManager.tlsScope.get() != this) {
            return;
        }

        if (finishOnClose) {
            wrapped.finish();
        }

        scopeManager.tlsScope.set(toRestore);
    }

    @Override
    public Span span() {
        return wrapped;
    }

}
