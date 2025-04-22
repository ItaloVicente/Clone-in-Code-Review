    @Override
    public Tracer tracer() {
        return tracer;
    }

    @Override
    public boolean tracingEnabled() {
        return tracer != null && !(tracer instanceof NoopTracer);
    }

