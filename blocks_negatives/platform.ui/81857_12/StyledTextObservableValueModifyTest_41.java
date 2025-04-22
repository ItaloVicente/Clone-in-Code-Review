public class StyledTextObservableValueModifyTest extends TestCase {
	public static Test suite() {
		TestSuite suite = new TestSuite(
				StyledTextObservableValueModifyTest.class.toString());
		suite.addTest(SWTMutableObservableValueContractTest
				.suite(new Delegate()));
		return suite;
