                                int numReplicas = conf.numberOfReplicas();

                                if (conf.ephemeral() && persistTo.value() != 0) {
                                    throw new ServiceNotAvailableException("Ephemeral Buckets do not support " +
                                            "PersistTo.");
                                }
                                if (replicateTo.touchesReplica() && replicateTo.value() > numReplicas) {
                                    throw new ReplicaNotConfiguredException("Not enough replicas configured on " +
                                            "the bucket.", cas);
                                }
                                if (persistTo.touchesReplica() && persistTo.value() - 1 > numReplicas) {
                                    throw new ReplicaNotConfiguredException("Not enough replicas configured on " +
                                            "the bucket.", cas);
                                }
                                return numReplicas;
