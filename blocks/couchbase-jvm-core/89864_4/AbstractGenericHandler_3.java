        if (env().tracingEnabled()) {
            Span dispatchSpan = dispatchSpans.poll();
            if (dispatchSpan != null) {
                currentDispatchSpan = dispatchSpan;
            }
        }

