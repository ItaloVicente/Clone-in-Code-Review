
            if (currentRequest != null && env() != null) {
                LatencyMetricIdentifier identifier = new LatencyMetricIdentifier(
                    ctx.channel().remoteAddress().toString(), // TODO optimize me
                    serviceType(),
                    currentRequest.getClass().getSimpleName()
                );
                LatencyMetricCollector collector = env().networkLatencyMetricCollector(); // TODO optimize me
                if (collector != null) {
                    collector.recordLatency(identifier, System.nanoTime() - currentStartTime);
                }
            }

