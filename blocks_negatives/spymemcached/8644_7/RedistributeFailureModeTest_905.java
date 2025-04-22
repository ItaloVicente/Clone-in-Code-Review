	@Override
	protected void initClient() throws Exception {
		initClient(new DefaultConnectionFactory() {
			@Override
			public FailureMode getFailureMode() {
				return FailureMode.Redistribute;
			}
		});
	}
