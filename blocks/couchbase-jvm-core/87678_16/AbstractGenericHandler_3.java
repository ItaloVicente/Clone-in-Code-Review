        this.channelId =  endpoint.environment().id() + "/" + endpoint.channelId();
        if (endpoint.environment().tracingEnabled() && endpoint.environment().tracer() instanceof SlowOperationTracer) {
            zombieReporter = ((SlowOperationTracer) endpoint.environment().tracer()).reporter();

        } else {
            zombieReporter = null;
        }
