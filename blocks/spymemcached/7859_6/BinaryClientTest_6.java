
	@Override
	protected void syncGetTimeoutsInitClient() throws Exception {
		initClient(new BinaryConnectionFactory() {
			@Override
			public long getOperationTimeout() {
				return 2;
			}

			@Override
			public int getTimeoutExceptionThreshold() {
				return 1000000;
			}
		});
	}
