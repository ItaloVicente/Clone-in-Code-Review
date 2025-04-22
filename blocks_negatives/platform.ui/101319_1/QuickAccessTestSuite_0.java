/**
 * Test areas of the Property Sheet API.
 */
public class QuickAccessTestSuite extends TestSuite {

    /**
     * Returns the suite.  This is required to
     * use the JUnit Launcher.
     */
    public static Test suite() {
        return new QuickAccessTestSuite();
    }

    /**
     * Construct the test suite.
     */
    public QuickAccessTestSuite() {
        addTest(new TestSuite(CamelUtilTest.class));
        addTest(new TestSuite(QuickAccessDialogTest.class));
		addTest(new TestSuite(ShellClosingTest.class));
    }
