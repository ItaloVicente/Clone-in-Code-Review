    /**
     * Returns all target replica nodes {@link InetAddress} which are currently available on the bucket.
     *
     * @param id the document ID to check.
     * @return the list of nodes for the given document ID.
     */
    public List<InetAddress> availableReplicaNodesForId(final String id) {
        BucketConfig config = bucketConfig.get();

        if (config instanceof CouchbaseBucketConfig) {
            CouchbaseBucketConfig cbc = (CouchbaseBucketConfig) config;
            List<InetAddress> replicas = new ArrayList<>();
            for (int i = 1; i <= cbc.numberOfReplicas(); i++) {
                try {
                    replicas.add(replicaNodeForId(id, i));
                } catch (IllegalStateException ex) {
                }
            }
            return replicas;
        } else {
            throw new UnsupportedOperationException("Bucket type not supported: " + config.getClass().getName());
        }
    }

