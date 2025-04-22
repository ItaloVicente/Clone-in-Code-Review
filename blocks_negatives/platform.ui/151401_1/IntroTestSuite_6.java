public class IntroTestSuite extends TestSuite {

	public static Test suite() {
		return new IntroTestSuite();
	}

	/**
	 *
	 */
	public IntroTestSuite() {
		addTest(new TestSuite(IntroPartTest.class));
		addTest(new TestSuite(NoIntroPartTest.class));
		addTest(new TestSuite(IntroTest.class));
	}
