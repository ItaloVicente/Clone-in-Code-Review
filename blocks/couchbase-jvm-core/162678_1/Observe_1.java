        int numReplicas = conf.numberOfReplicas();
        int requiredReplicas = Math.max(replicateTo.value(), persistTo.value() - 1);
        if (requiredReplicas > 0) {
            if (requiredReplicas > numReplicas) {
                throw new ReplicaNotConfiguredException(
                    "Not enough replicas configured on the bucket;" +
                        " required=" + requiredReplicas + ", configured=" + numReplicas,
                    cas);
            }

            int availableReplicas = availableReplicas(id, conf);
            if (requiredReplicas > availableReplicas) {
                throw new ReplicaNotAvailableException(
                    "Not enough replicas are currently available;" +
                        " required=" + requiredReplicas + ", available=" + availableReplicas,
                    cas);
            }
        }
    }

    private static int availableReplicas(String id, CouchbaseBucketConfig conf) {
        int partition = partitionForKey(id.getBytes(UTF_8), conf.numberOfPartitions());
        int availableReplicas = 0;
        for (int i = 0, numberOfReplicas = conf.numberOfReplicas(); i < numberOfReplicas; i++) {
            if (conf.nodeIndexForReplica(partition, i, false) >= 0) {
                availableReplicas++;
            }
        }
        return availableReplicas;
    }
