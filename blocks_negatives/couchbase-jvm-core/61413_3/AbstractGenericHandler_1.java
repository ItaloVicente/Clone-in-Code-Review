            NetworkLatencyMetricsIdentifier identifier = new NetworkLatencyMetricsIdentifier(
                    remoteHostname,
                    serviceType().toString(),
                    simpleName,
                    response.status().toString()
            );
            env().networkLatencyMetricsCollector().record(identifier, currentOpTime);
