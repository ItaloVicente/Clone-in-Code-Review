            if (currentRequest != null && env() != null) {
                MetricIdentifier metricIdentifier = new MetricIdentifier(
                        ctx.channel().remoteAddress().toString(),
                        serviceType(),
                        currentRequest.getClass().getSimpleName()
                );
                MetricsCollector collector = env().metricsCollector();
                if (collector != null) {
                    env().metricsCollector().recordLatency(metricIdentifier, System.nanoTime() - startWireTime);
                }
            }
