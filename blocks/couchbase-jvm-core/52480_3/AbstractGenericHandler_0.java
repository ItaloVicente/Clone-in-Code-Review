
            if (currentRequest != null && env() != null) {
                MetricIdentifier identifier = new MetricIdentifier(
                    ctx.channel().remoteAddress().toString(), // TODO optimize me
                    serviceType(),
                    currentRequest.getClass().getSimpleName()
                );
                MetricCollector collector = env().metricCollector(); // TODO optimize me
                if (collector != null) {
                    collector.recordLatency(identifier, System.nanoTime() - currentStartTime);
                }
            }

