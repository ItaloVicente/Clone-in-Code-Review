	@Rule
	public TestWatcher testWatcher = new TestWatcher() {
		@Override
		protected void starting(Description description) {
			runningTest = description.getMethodName();
		}
		@Override
		protected void finished(Description description) {
			runningTest = null;
		}
	};
	private String runningTest = null;
