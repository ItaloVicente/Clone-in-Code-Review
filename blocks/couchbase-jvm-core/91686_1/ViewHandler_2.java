        if (currentRequest().span() != null) {
            if (env().tracingEnabled()) {
                env().tracer().scopeManager()
                    .activate(currentRequest().span(), true)
                    .close();
            }
        }
