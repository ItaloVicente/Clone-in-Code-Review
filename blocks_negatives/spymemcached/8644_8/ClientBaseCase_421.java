	protected MemcachedClient client = null;
	protected Boolean membase;
	protected Boolean moxi;

	protected void initClient() throws Exception {
		initClient(new DefaultConnectionFactory() {
			@Override
			public long getOperationTimeout() {
				return 15000;
			}
			@Override
			public FailureMode getFailureMode() {
				return FailureMode.Retry;
			}
		});
	}
