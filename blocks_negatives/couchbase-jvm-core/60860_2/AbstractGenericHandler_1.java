                    if (currentRequest != null && currentOpTime >= 0 && env() != null && env().networkLatencyMetricsCollector().isEnabled()) {
                        Class<? extends CouchbaseRequest> requestClass = currentRequest.getClass();
                        String simpleName = classNameCache.get(requestClass);
                        if (simpleName == null) {
                            simpleName = requestClass.getSimpleName();
                            classNameCache.put(requestClass, simpleName);
                        }

                        NetworkLatencyMetricsIdentifier identifier = new NetworkLatencyMetricsIdentifier(
                            remoteHostname,
                            serviceType().toString(),
                            simpleName,
                            response.status().toString()
                        );
                        env().networkLatencyMetricsCollector().record(identifier, currentOpTime);
                    }
