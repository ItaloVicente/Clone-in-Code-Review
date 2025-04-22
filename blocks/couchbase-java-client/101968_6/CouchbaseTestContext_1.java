                                bucket = authed ? cluster.openBucket(bucketName) :
                                        cluster.openBucket(bucketName, bucketPassword);
                                PingReport pingReport = bucket.ping();
                                for (PingServiceHealth health:pingReport.services()) {
                                    if (health.state() != PingServiceHealth.PingState.OK) {
                                        throw new Exception("Not healthy");
                                    }
                                }
                                bucket.upsert(JsonDocument.create(bucketName + "foo"));
                                bucket.remove(bucketName + "foo");
                                canUseBucket = true;
