@RunWith(org.junit.runners.AllTests.class)
public final class ContextsTestSuite extends TestSuite {

	/**
	 * Returns the suite. This is required to use the JUnit Launcher.
	 */
	public static final Test suite() {
		return new ContextsTestSuite();
	}

	/**
	 * Constructs a new instance of <code>ContextsTestSuite</code> with all of
	 * the relevent test cases.
	 */
	public ContextsTestSuite() {
		addTestSuite(Bug74990Test.class);
		addTestSuite(Bug84763Test.class);
		addTestSuite(ExtensionTestCase.class);
		addTestSuite(PartContextTest.class);
	}
