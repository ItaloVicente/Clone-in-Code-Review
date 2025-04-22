	@Override
	protected void doSetUp() throws Exception {
		super.doSetUp();
		FinishedJobs.getInstance().clearAll();
	}

	@Override
	protected void doTearDown() throws Exception {
		FinishedJobs.getInstance().clearAll();
		super.doTearDown();
	}
