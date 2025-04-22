	
	protected void syncGetTimeoutsInitClient() throws Exception {
		initClient(new DefaultConnectionFactory() {
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
