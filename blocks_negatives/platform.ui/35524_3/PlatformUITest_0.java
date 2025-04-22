public class PlatformUITest extends TestCase {
	public static TestSuite suite() {
		TestSuite suite = new TestSuite("org.eclipse.ui.tests.rcp.PlatformUITest");
		suite.addTest(new PlatformUITest("testEarlyGetWorkbench"));
		suite.addTest(new PlatformUITest("testCreateDisplay"));
		suite.addTest(new PlatformUITest("testCreateAndRunWorkbench"));
		suite.addTest(new PlatformUITest("testCreateAndRunWorkbenchWithExceptionOnStartup"));
		return suite;
	}
