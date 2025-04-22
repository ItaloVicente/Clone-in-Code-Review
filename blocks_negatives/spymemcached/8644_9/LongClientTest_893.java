	public void testParallelGet() throws Throwable {
		client.shutdown();
		initClient(new DefaultConnectionFactory(){
			@Override
			public MemcachedConnection createConnection(
					List<InetSocketAddress> addrs) throws IOException {
				MemcachedConnection rv = super.createConnection(addrs);
				return rv;
			}
			@Override
			public long getOperationTimeout() {
				return 15000;
			}
			@Override
			public boolean shouldOptimize() {
				return false;
			}
			});
