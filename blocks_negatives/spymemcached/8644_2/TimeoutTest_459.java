	@Override
	protected void initClient() throws Exception {
		client=new MemcachedClient(new DefaultConnectionFactory() {
			@Override
			public long getOperationTimeout() {
				return 20;
			}
			@Override
			public FailureMode getFailureMode() {
				return FailureMode.Retry;
			}},
			AddrUtil.getAddresses(TestConfig.IPV4_ADDR + ":64213"));
	}
