@RunWith(org.junit.runners.AllTests.class)
public class MultiPageEditorTestSuite extends TestSuite {

	/**
	 * Returns the suite. This is required to use the JUnit Launcher.
	 * @return A new test suite; never <code>null</code>.;
	 */
	public static Test suite() {
		return new MultiPageEditorTestSuite();
	}

	/**
	 * Construct the test suite.
	 */
	public MultiPageEditorTestSuite() {
		addTestSuite(MultiEditorInputTest.class);
		addTestSuite(MultiPageEditorPartTest.class);
		addTestSuite(MultiVariablePageTest.class);
	}
