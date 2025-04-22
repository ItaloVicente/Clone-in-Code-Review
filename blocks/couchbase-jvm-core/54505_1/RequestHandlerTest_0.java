    @Test
    public void shouldNotFailReconfigureOnRemoveAllRaceCondition() throws InterruptedException {
        final ClusterConfig config = mock(DefaultClusterConfig.class);
        when(config.bucketConfigs()).thenReturn(Collections.<String, BucketConfig>emptyMap());
        final Subject<ClusterConfig, ClusterConfig> configObservable = PublishSubject.<ClusterConfig>create();

        final Set<Node> nodes = Mockito.spy(new HashSet<Node>());
        when(nodes.isEmpty()).thenReturn(false);

        final RequestHandler handler = new RequestHandler(nodes, environment, configObservable, null);

        final Node node1 = mock(Node.class);
        when(node1.connect()).thenReturn(Observable.just(LifecycleState.CONNECTED));
        when(node1.disconnect()).thenReturn(Observable.just(LifecycleState.DISCONNECTED));
        final Node node2 = mock(Node.class);
        when(node2.connect()).thenReturn(Observable.just(LifecycleState.CONNECTED));
        when(node2.disconnect()).thenReturn(Observable.just(LifecycleState.DISCONNECTED));
        final Node node3 = mock(Node.class);
        when(node3.connect()).thenReturn(Observable.just(LifecycleState.CONNECTED));
        when(node3.disconnect()).thenReturn(Observable.just(LifecycleState.DISCONNECTED));

        handler.addNode(node1).toBlocking().single();
        handler.addNode(node2).toBlocking().single();
        handler.addNode(node3).toBlocking().single();

        try {
            handler.reconfigure(config).toBlocking().single();
        } catch (NoSuchElementException e) {
            fail("failed to remove all nodes on first pass - " + e);
        }

        try {
            handler.reconfigure(config).toBlocking().single();
        } catch (NoSuchElementException e) {
            fail("race condition on removing all during reconfigure - " + e);
        }
    }

