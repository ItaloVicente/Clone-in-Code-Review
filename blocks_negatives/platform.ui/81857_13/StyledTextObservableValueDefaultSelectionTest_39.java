public class StyledTextObservableValueDefaultSelectionTest extends TestCase {
	public static Test suite() {
		TestSuite suite = new TestSuite(
				StyledTextObservableValueDefaultSelectionTest.class.toString());
		suite.addTest(SWTMutableObservableValueContractTest
				.suite(new Delegate()));
		return suite;
