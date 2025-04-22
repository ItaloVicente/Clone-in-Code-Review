@RunWith(org.junit.runners.AllTests.class)
public class FilteredResourcesSelectionDialogTestSuite extends TestSuite {

	/**
	 * Returns the suite. This is required to use the JUnit Launcher.
	 *
	 * @return A new test suite; never <code>null</code>.;
	 */
	public static Test suite() {
		return new FilteredResourcesSelectionDialogTestSuite();
	}

	/**
	 * Construct the test suite.
	 */
	public FilteredResourcesSelectionDialogTestSuite() {
		addTestSuite(ResourceItemLabelTest.class);
		addTestSuite(ResourceInitialSelectionTest.class);
		addTestSuite(ResourceSelectionFilteringDialogTest.class);
	}
