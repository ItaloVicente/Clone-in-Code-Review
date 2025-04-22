                NetworkLatencyMetricsIdentifier identifier = new NetworkLatencyMetricsIdentifier(
                        remoteHostname,
                        serviceType().toString(),
                        simpleName,
                        response.status().toString()
                );
                env().networkLatencyMetricsCollector().record(identifier, currentOpTime);
            } catch (Exception ex) {
                LOGGER.warn("Could not collect latency metric for request + "
                    + currentRequest + "(" + currentOpTime + ")", ex);
            }
