	@Override
	protected void initClient() throws Exception {
		initClient(new BinaryConnectionFactory() {
			@Override
			public FailureMode getFailureMode() {
				return FailureMode.Retry;
			}
		});
	}

