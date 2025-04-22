	public static TestSuite suite() {
		TestSuite ts = new TestSuite();
		ts.addTest(new WorkingSetTests("testWorkingSetUpdater"));
		ts.addTest(new WorkingSetTests("testWorkingSetWithBasicElementAdapter"));
		ts.addTest(new WorkingSetTests("testWorkingSetWithCustomElementAdapter"));
		return ts;
	}

	/**
	 * @param testName
	 */
	public WorkingSetTests(String testName) {
		super(testName);
