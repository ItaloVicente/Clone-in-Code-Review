public class TextObservableValueModifyTest extends TestCase {
	public static Test suite() {
		TestSuite suite = new TestSuite(TextObservableValueModifyTest.class
				.toString());
		suite.addTest(SWTMutableObservableValueContractTest
				.suite(new Delegate()));
		return suite;
