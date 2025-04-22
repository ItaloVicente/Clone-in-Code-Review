        return OPENTRACING_PRESENT && tracingEnabled;
    }

    @Override
    public Tracer tracer() {
        return tracer;
