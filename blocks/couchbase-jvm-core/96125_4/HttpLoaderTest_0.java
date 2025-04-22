    @Test
    public void verifyRightTersePath() {
        ClusterFacade cluster = mock(ClusterFacade.class);
        Observable<CouchbaseResponse> terseResponse = Observable.just(
                (CouchbaseResponse) new BucketConfigResponse("terseConfig", ResponseStatus.SUCCESS)
        );
        when(cluster.send(isA(BucketConfigRequest.class))).thenReturn(terseResponse);
        HttpLoader loader = new HttpLoader(cluster, environment);
        loader.discoverConfig("default", "bucket", "password", host).toBlocking().single();

        ArgumentCaptor<BucketConfigRequest> argument = ArgumentCaptor.forClass(BucketConfigRequest.class);
        verify(cluster).send(argument.capture());
        Assert.assertEquals("/pools/default/b/default", argument.getValue().path());
    }
