            if (env().tracingEnabled() && currentDispatchSpan() != null) {
                currentDispatchSpan().setTag("peer.latency", duration + "us");
                if (currentDispatchSpan() instanceof ThresholdLogSpan) {
                    currentDispatchSpan().setBaggageItem(
                        ThresholdLogReporter.KEY_SERVER_MICROS,
                        Long.toString(duration)
                    );
                }
            }
