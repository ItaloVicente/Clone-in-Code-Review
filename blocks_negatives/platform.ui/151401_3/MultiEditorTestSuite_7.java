public class MultiEditorTestSuite extends TestSuite {

	public static Test suite() {
		return new MultiEditorTestSuite();
	}
	/**
	 * Construct the test suite.
	 */
	public MultiEditorTestSuite() {
		addTestSuite(AbstractMultiEditorTest.class);
		addTestSuite(MultiEditorTest.class);
	}
