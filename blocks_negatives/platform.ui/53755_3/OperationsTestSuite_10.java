public class OperationsTestSuite extends TestSuite {
	/**
	 * Returns the suite. This is required to use the JUnit Launcher.
	 */
	public static final Test suite() {
		return new OperationsTestSuite();
	}

	/**
	 * Construct the test suite.
	 */
	public OperationsTestSuite() {
		addTest(new TestSuite(OperationsAPITest.class));
		addTest(new TestSuite(WorkbenchOperationHistoryTests.class));
		addTest(new TestSuite(MultiThreadedOperationsTests.class));
		addTest(new TestSuite(WorkbenchOperationStressTests.class));
		addTest(new TestSuite(WorkspaceOperationsTests.class));
	}
