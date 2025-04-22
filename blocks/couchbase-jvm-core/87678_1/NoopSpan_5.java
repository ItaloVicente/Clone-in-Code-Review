package com.couchbase.client.core.tracing.noop;

import io.opentracing.Scope;
import io.opentracing.ScopeManager;
import io.opentracing.Span;

public interface NoopScopeManager extends ScopeManager {
    NoopScopeManager INSTANCE = new NoopScopeManagerImpl();

    interface NoopScope extends Scope {
        NoopScope INSTANCE = new NoopScopeManagerImpl.NoopScopeImpl();
    }
}

class NoopScopeManagerImpl implements NoopScopeManager {
    @Override
    public Scope activate(Span span, boolean finishOnClose) {
        return NoopScope.INSTANCE;
    }

    @Override
    public Scope active() {
        return null;
    }

    static class NoopScopeImpl implements NoopScope {
        @Override
        public void close() {}

        @Override
        public Span span() {
            return NoopSpan.INSTANCE;
        }
    }
}
