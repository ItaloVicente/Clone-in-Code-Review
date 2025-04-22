        return call(core, bucket, id, cas, remove, persistTo, replicateTo, DEFAULT_DELAY);
    }

    public static Observable<Boolean> call(final ClusterFacade core, final String bucket, final String id,
        final long cas, final boolean remove, final PersistTo persistTo, final ReplicateTo replicateTo,
        final Delay delay) {
