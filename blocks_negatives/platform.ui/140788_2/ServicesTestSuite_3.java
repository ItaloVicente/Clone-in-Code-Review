@RunWith(org.junit.runners.AllTests.class)
public final class ServicesTestSuite extends TestSuite {

	/**
	 * Returns the suite. This is required to use the JUnit Launcher.
	 */
	public static final Test suite() {
		return new ServicesTestSuite();
	}

	/**
	 * Construct the test suite.
	 */
	public ServicesTestSuite() {
		addTest(new TestSuite(EvaluationServiceTest.class));
		addTest(ContributedServiceTest.suite());
		addTest(new TestSuite(WorkbenchSiteProgressServiceTest.class));
	}
