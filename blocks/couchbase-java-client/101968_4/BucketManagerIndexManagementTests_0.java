                PingReport pingReport = indexedBucket.ping();
                for (PingServiceHealth health:pingReport.services()) {
                    if (health.state() != PingServiceHealth.PingState.OK) {
                        throw new Exception("Not healthy");
                    }
                }
                indexedBucket.upsert(JsonDocument.create(indexedBucketName + "foo"));
                indexedBucket.remove(indexedBucketName + "foo");
                canUseBucket = true;
