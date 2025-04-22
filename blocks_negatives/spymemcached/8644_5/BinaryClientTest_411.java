	@Override
	protected void syncGetTimeoutsInitClient() throws Exception {
		initClient(new BinaryConnectionFactory() {
			@Override
			public long getOperationTimeout() {
				return 2;
			}
