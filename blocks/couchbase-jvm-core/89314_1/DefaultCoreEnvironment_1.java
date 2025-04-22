        tracingEnabled = booleanPropertyOr("tracingEnabled", builder.tracingEnabled);
        if (!OPENTRACING_PRESENT) {
            tracer = null;
        } else if(!tracingEnabled) {
            tracer = null;
        } else if (builder.tracer == null) {
            tracer = null;
        } else {
            tracer = builder.tracer;
        }
