public class TextObservableValueDefaultSelectionTest extends TestCase {
	public static Test suite() {
		TestSuite suite = new TestSuite(TextObservableValueDefaultSelectionTest.class
				.toString());
		suite.addTest(SWTMutableObservableValueContractTest
				.suite(new Delegate()));
		return suite;
