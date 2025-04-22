@RunWith(AllTests.class)
public class MenusTestSuite extends TestSuite {

    /**
     * Returns the suite. This is required to use the JUnit Launcher.
     */
    public static Test suite() {
        return new MenusTestSuite();
    }

    /**
     * Construct the test suite.
     */
    public MenusTestSuite() {
		/*
		 * TODO: MenusTestSuite was previously disabled in UiTestSuite and these
		 * commented-out tests must be validated and fixed up
		 */
		addTest(new TestSuite(DynamicToolbarTest.class));
    }
