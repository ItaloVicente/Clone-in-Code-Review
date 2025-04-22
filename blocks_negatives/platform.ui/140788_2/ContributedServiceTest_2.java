	public static TestSuite suite() {
		TestSuite ts = new TestSuite();
		ts.addTest(new ContributedServiceTest("testGlobalService"));
		ts.addTest(new ContributedServiceTest("testWorkbenchServiceFactory"));
		return ts;
	}
	/**
	 * @param testName
	 */
	public ContributedServiceTest(String testName) {
		super(testName);
