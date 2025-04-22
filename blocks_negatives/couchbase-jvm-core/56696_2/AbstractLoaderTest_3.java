    @Test
    public void shouldLoadConfigsFromMoreSeedNodes() throws Exception {
        ClusterFacade cluster = mock(ClusterFacade.class);
        when(cluster.send(isA(AddNodeRequest.class))).thenReturn(
                Observable.just((CouchbaseResponse) new AddNodeResponse(ResponseStatus.SUCCESS, host))
        );
        when(cluster.send(isA(AddServiceRequest.class))).thenReturn(
                Observable.just((CouchbaseResponse) new AddServiceResponse(ResponseStatus.SUCCESS, host))
        );
        Set<InetAddress> seedNodes = new HashSet<InetAddress>();
        seedNodes.add(InetAddress.getByName("10.1.1.1"));
        seedNodes.add(InetAddress.getByName("10.1.1.2"));
        seedNodes.add(InetAddress.getByName("10.1.1.3"));

        InstrumentedLoader loader = new InstrumentedLoader(99, localhostConfig, cluster, environment);
        Observable<Tuple2<LoaderType, BucketConfig>> configObservable = loader.loadConfig(seedNodes, "default", "password");

        List<Tuple2<LoaderType, BucketConfig>> loadedConfigs = configObservable.toList().toBlocking().single();
        assertEquals(1, loadedConfigs.size());
    }

