                if (currentDispatchSpan != null) {
                    env().tracer().scopeManager()
                        .activate(currentDispatchSpan, true)
                        .close();
                    if (currentDispatchSpan instanceof ThresholdLogSpan) {
                        currentDispatchSpan.setBaggageItem(
                            ThresholdLogReporter.KEY_DISPATCH_MICROS,
                            Long.toString(((ThresholdLogSpan) currentDispatchSpan).durationMicros())
                        );
                    }
                    currentDispatchSpan = null;
                }

