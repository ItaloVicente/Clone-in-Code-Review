    public static Observable<Boolean> call(final ClusterFacade core, final String bucket, final String id,
        final long cas, final boolean remove, MutationToken token, final PersistTo persistTo, final ReplicateTo replicateTo,
        final Delay delay, final RetryStrategy retryStrategy) {
        return call(core, bucket, id, cas, remove, token, persistTo, replicateTo, delay, retryStrategy, null);
    }

    public static Observable<Boolean> call(final ClusterFacade core, final String bucket, final String id,
        final long cas, final boolean remove, final PersistTo persistTo, final ReplicateTo replicateTo,
        final RetryStrategy retryStrategy, Span parent) {
        return call(core, bucket, id, cas, remove, persistTo, replicateTo, DEFAULT_DELAY, retryStrategy, parent);
    }

    public static Observable<Boolean> call(final ClusterFacade core, final String bucket, final String id,
        final long cas, final boolean remove, final PersistTo persistTo, final ReplicateTo replicateTo,
        final Delay delay, final RetryStrategy retryStrategy, Span parent) {
        return call(core, bucket, id, cas, remove, null, persistTo, replicateTo, delay, retryStrategy, parent);
    }

