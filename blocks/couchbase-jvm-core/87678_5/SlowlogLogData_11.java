package com.couchbase.client.core.tracing;

public final class NoopTracerFactory {
    
    public static NoopTracer create() {
        return NoopTracerImpl.INSTANCE;
    }

    private NoopTracerFactory() {}
}

