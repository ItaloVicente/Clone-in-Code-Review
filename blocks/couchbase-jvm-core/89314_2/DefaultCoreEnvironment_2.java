        return OPENTRACING_PRESENT && tracingEnabled;
    }

    @Override
    public io.opentracing.Tracer tracer() {
        return tracer;
