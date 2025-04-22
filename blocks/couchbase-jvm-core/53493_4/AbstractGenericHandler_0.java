
                if (currentDecodingState == DecodingState.FINISHED) {
                    if (currentRequest != null && env() != null && env().networkLatencyMetricsCollector().isEnabled()) {
                        NetworkLatencyMetricsIdentifier identifier = new NetworkLatencyMetricsIdentifier(
                            remoteHostname,
                            serviceType().toString(),
                            currentRequest.getClass().getSimpleName(),
                            response.status().toString()
                        );
                        env().networkLatencyMetricsCollector().record(identifier, currentOpTime);
                    }
                }

