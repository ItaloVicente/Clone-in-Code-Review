        nodesToShutdown = new ConcurrentLinkedQueue<MemcachedNode>();
        this.bufSize = bufSize;
        this.connectionFactory = f;
        List<MemcachedNode> connections = createConnections(a);
        locator=f.createLocator(connections);
	}
    private List<MemcachedNode> createConnections(final Collection<InetSocketAddress> a)
        throws IOException {
