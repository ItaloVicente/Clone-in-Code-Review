                PingReport pingReport = indexedBucket.ping();
                for (PingServiceHealth health:pingReport.services()) {
                    if (health.state() != PingServiceHealth.PingState.OK) {
                        throw new Exception("Not healthy");
                    }
                }
