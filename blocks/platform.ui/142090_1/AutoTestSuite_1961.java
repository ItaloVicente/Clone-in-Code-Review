	private AutoTestLogger logger;

	public AutoTestSuite(URL expectedResults) {
		if (expectedResults == null) {
			logger = new AutoTestLogger();
		} else {
			try {
				logger = new AutoTestLogger(expectedResults);
			} catch (WorkbenchException e) {
				logger = new AutoTestLogger();
				e.printStackTrace();
			}
		}
	}

	protected AutoTestLogger getLog() {
		return logger;
	}

	public void addWrapper(AutoTest test) {
		addTest(new AutoTestWrapper(test, logger));
	}

	@Override
