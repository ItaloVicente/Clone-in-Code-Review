                                        (CouchbaseBucketConfig) response.config().bucketConfig(bucket);
                                int numReplicas = conf.numberOfReplicas();

                                if (replicateTo.touchesReplica() && replicateTo.value() > numReplicas) {
                                    throw new ReplicaNotConfiguredException("Not enough replicas configured on " +
                                            "the bucket.");
                                }
                                if (persistTo.touchesReplica() && persistTo.value() - 1 > numReplicas) {
                                    throw new ReplicaNotConfiguredException("Not enough replicas configured on " +
                                            "the bucket.");
                                }
                                return numReplicas;
