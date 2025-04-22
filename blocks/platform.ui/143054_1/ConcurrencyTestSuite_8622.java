	public ConcurrencyTestSuite() {
		addTestSuite(ModalContextCrashTest.class);
		addTestSuite(NestedSyncExecDeadlockTest.class);
		addTestSuite(SyncExecWhileUIThreadWaitsForRuleTest.class);
		addTestSuite(SyncExecWhileUIThreadWaitsForLock.class);
		addTestSuite(TestBug105491.class);
		addTestSuite(TestBug108162.class);
		addTestSuite(TestBug138695.class);
		addTestSuite(TestBug98621.class);
		addTestSuite(TransferRuleTest.class);
		addTestSuite(Bug_262032.class);
		addTestSuite(TestBug269121.class);
	}
