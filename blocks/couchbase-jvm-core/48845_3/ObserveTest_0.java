    @Test
    public void shouldAlwaysAskActiveNode() {
        ClusterFacade cluster = mock(ClusterFacade.class);

        CouchbaseBucketConfig bucketConfig = mock(CouchbaseBucketConfig.class);
        when(bucketConfig.numberOfReplicas()).thenReturn(1);
        ClusterConfig clusterConfig = mock(ClusterConfig.class);
        when(clusterConfig.bucketConfig("bucket")).thenReturn(bucketConfig);
        GetClusterConfigResponse clusterConfigResponse = new GetClusterConfigResponse(
            clusterConfig, ResponseStatus.SUCCESS
        );
        when(cluster.send(isA(GetClusterConfigRequest.class))).thenReturn(
            Observable.just((CouchbaseResponse) clusterConfigResponse)
        );
        ObserveResponse observeResponse = new ObserveResponse(
            ResponseStatus.SUCCESS,
            ObserveResponse.ObserveStatus.FOUND_NOT_PERSISTED.value(),
            false,
            45678,
            "bucket",
            mock(CouchbaseRequest.class)
        );
        when(cluster.send(isA(ObserveRequest.class))).thenReturn(
            Observable.just((CouchbaseResponse) observeResponse)
        );

        Observable<Boolean> result = Observe.call(
            cluster, "bucket", "id", 45678, false, Observe.PersistTo.NONE, Observe.ReplicateTo.ONE,
            BestEffortRetryStrategy.INSTANCE
        );
        result
            .timeout(5, TimeUnit.SECONDS)
            .toBlocking()
            .single();

        verify(cluster, times(2)).send(isA(ObserveRequest.class));
    }

