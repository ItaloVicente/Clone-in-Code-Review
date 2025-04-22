        if (env().tracingEnabled()) {
            Span dispatchSpan = dispatchSpans.poll();
            if (dispatchSpan != null) {
                env().tracer().scopeManager()
                    .activate(dispatchSpan, true)
                    .close();
            }
        }

