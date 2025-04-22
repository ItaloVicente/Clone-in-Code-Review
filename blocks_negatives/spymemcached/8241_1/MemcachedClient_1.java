		throws IOException {
		this(cf, addrs, true);
	}

	protected MemcachedClient(ConnectionFactory cf, List<InetSocketAddress> addrs,
			boolean startIOThread) throws IOException {
