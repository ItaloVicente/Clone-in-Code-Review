    public List<InetAddress> availableReplicaNodesForId(final String id) {
        BucketConfig config = bucketConfig.get();

        if (config instanceof CouchbaseBucketConfig) {
            CouchbaseBucketConfig cbc = (CouchbaseBucketConfig) config;
            List<InetAddress> replicas = new ArrayList<>();
            for (int i = 1; i <= cbc.numberOfReplicas(); i++) {
                InetAddress foundReplica = replicaNodeForId(id, i, false);
                if (foundReplica != null) {
                    replicas.add(foundReplica);
                }
            }
            return replicas;
        } else {
            throw new UnsupportedOperationException("Bucket type not supported: " + config.getClass().getName());
        }
    }

