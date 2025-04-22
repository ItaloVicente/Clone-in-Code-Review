		startTime = System.currentTimeMillis();
		new TimeoutReportJob(getName(), 60 * 1000).schedule();
	}

	@Override
	protected void doTearDown() throws Exception {
		teardownCalled = true;
		super.doTearDown();
