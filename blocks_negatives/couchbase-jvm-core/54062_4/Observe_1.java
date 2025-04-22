    private static Observable<ObserveResponse> sendObserveRequests(final ClusterFacade core, final String bucket,
        final String id, final long cas, final PersistTo persistTo, final ReplicateTo replicateTo,
        final RetryStrategy retryStrategy) {
        final boolean swallowErrors = retryStrategy.shouldRetryObserve();
        return Observable.defer(new Func0<Observable<ObserveResponse>>() {
            @Override
            public Observable<ObserveResponse> call() {
                return core
                        .<GetClusterConfigResponse>send(new GetClusterConfigRequest())
                        .map(new Func1<GetClusterConfigResponse, Integer>() {
                            @Override
                            public Integer call(GetClusterConfigResponse response) {
                                CouchbaseBucketConfig conf =
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
                            }
                        })
                        .flatMap(new Func1<Integer, Observable<ObserveResponse>>() {
                            @Override
                            public Observable<ObserveResponse> call(Integer replicas) {
                                List<Observable<ObserveResponse>> obs = new ArrayList<Observable<ObserveResponse>>();
                                Observable<ObserveResponse> masterRes = core.send(new ObserveRequest(id, cas, true, (short) 0, bucket));
                                if (swallowErrors) {
                                    obs.add(masterRes.onErrorResumeNext(Observable.<ObserveResponse>empty()));
                                } else {
                                    obs.add(masterRes);
                                }

                                if (persistTo.touchesReplica() || replicateTo.touchesReplica()) {
                                    for (short i = 1; i <= replicas; i++) {
                                        Observable<ObserveResponse> res = core.send(new ObserveRequest(id, cas, false, i, bucket));
                                        if (swallowErrors) {
                                            obs.add(res.onErrorResumeNext(Observable.<ObserveResponse>empty()));
                                        } else {
                                            obs.add(res);
                                        }
                                    }
                                }

                                if (obs.size() == 1) {
                                    return obs.get(0);
                                } else {
                                    return Observable.mergeDelayError(Observable.from(obs));
                                }
                            }
                        });
            }
        });
    }

    /**
     * An immutable state class that can be constructed from an {@link ObserveResponse}
     * and aggregated with other intermediary states during a {@link Observable#scan(Func2) scan}.
     *
     * This encapsulates the logic of tracking observed state and checking it against a
     * {@link ReplicateTo replication} or {@link PersistTo persistence} criteria.
     *
     * Having each intermediate state will allow to shortcut as soon as the desired state is
     * observed, not until all the replicas have manifested themselves.
     */
    private static class ObserveItem {

        private final int replicated;
        private final int persisted;
        private final boolean persistedMaster;


        /**
         * Build an {@link ObserveItem} state from a {@link ObserveResponse}.
         *
         * @param id the observed key.
         * @param response the {@link ObserveResponse} received for that key.
         * @param cas the cas that we expect.
         * @param remove true if this is a remove operation, false otherwise.
         * @param persistIdentifier the {@link ObserveResponse.ObserveStatus} to watch for in persistence.
         * @param replicaIdentifier the {@link ObserveResponse.ObserveStatus} to watch for in replication.
         * @throws DocumentConcurrentlyModifiedException if the cas observed on master copy isn't the expected one.
         */
        public ObserveItem(String id, ObserveResponse response, long cas, boolean remove,
                           ObserveResponse.ObserveStatus persistIdentifier,
                           ObserveResponse.ObserveStatus replicaIdentifier) {
            int replicated = 0;
            int persisted = 0;
            boolean persistedMaster = false;


            if (response.content() != null && response.content().refCnt() > 0) {
                response.content().release();
            }
            ObserveResponse.ObserveStatus status = response.observeStatus();

            boolean validCas = cas == response.cas()
                    || (remove && response.cas() == 0 && status == persistIdentifier);

            if (response.master()) {
                if (!validCas) {
                    throw new DocumentConcurrentlyModifiedException("The CAS on the active node "
                            + "changed for ID \"" + id + "\", indicating it has been modified in the "
                            + "meantime.");
                }

                if (status == persistIdentifier) {
                    persisted++;
                    persistedMaster = true;
                }
            } else if (validCas) {
                if (status == persistIdentifier) {
                    persisted++;
                    replicated++;
                } else if (status == replicaIdentifier) {
                    replicated++;
                }
            }
