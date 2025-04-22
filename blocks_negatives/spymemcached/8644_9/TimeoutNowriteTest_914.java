	@Override
	protected void initClient() throws Exception {
		client=new MemcachedClient(new DefaultConnectionFactory() {
			@Override
			public long getOperationTimeout() {
			}
			@Override
			public FailureMode getFailureMode() {
				return FailureMode.Retry;
			}},
			AddrUtil.getAddresses(TestConfig.IPV4_ADDR + ":11211"));
	}
