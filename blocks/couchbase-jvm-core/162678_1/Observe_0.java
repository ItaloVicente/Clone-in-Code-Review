    public static void checkReplicasAreAvailable(
        final CouchbaseBucketConfig conf,
        final PersistTo persistTo,
        final ReplicateTo replicateTo,
        final long cas,
        final String id) {

        if (conf.ephemeral() && persistTo.value() != 0) {
            throw new ServiceNotAvailableException("Ephemeral buckets do not support PersistTo.");
        }
