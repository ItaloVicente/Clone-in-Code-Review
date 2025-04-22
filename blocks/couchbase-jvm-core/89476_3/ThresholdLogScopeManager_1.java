
package com.couchbase.client.core.tracing;

import io.opentracing.Scope;
import io.opentracing.Span;

public class ThresholdLogScope implements Scope {

    private final ThresholdLogScopeManager scopeManager;
    private final Span wrapped;
    private final boolean finishOnClose;
    private final ThresholdLogScope toRestore;

    ThresholdLogScope(final ThresholdLogScopeManager scopeManager, final Span wrapped,
                      final boolean finishOnClose) {
        this.scopeManager = scopeManager;
        this.wrapped = wrapped;
        this.finishOnClose = finishOnClose;
        this.toRestore = scopeManager.scope.get();
        scopeManager.scope.set(this);
    }

    @Override
    public void close() {
        if (scopeManager.scope.get() != this) {
            return;
        }
        if (finishOnClose) {
            wrapped.finish();
        }
        scopeManager.scope.set(toRestore);
    }

    @Override
    public Span span() {
        return wrapped;
    }
}
