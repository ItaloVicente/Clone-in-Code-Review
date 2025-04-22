        tracingEnabled = booleanPropertyOr("tracingEnabled", builder.tracingEnabled);
        if (!tracingEnabled) {
            tracer = NoopTracerFactory.create();
        } else if (builder.tracer == null) {
            tracer = null;
        } else {
            tracer = builder.tracer;
        }
