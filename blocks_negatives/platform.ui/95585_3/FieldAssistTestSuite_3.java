public class FieldAssistTestSuite extends TestSuite {
	/**
	 * Returns the suite. This is required to use the JUnit Launcher.
	 */
	public static final Test suite() {
		return new FieldAssistTestSuite();
	}

	/**
	 * Construct the test suite.
	 */
	public FieldAssistTestSuite() {
		addTest(new TestSuite(ControlDecorationTests.class));
		addTest(new TestSuite(FieldAssistAPITests.class));
	}
