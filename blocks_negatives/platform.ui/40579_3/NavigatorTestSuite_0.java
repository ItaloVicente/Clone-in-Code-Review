public final class NavigatorTestSuite extends TestSuite {

	/**
	 * Returns the suite. This is required to use the JUnit Launcher.
	 */
	public static final Test suite() {
		return new NavigatorTestSuite();
	}

	/**
	 * Construct the test suite.
	 */
	public NavigatorTestSuite() {
		addTest(new TestSuite(InitialActivationTest.class));
		addTest(new TestSuite(ActionProviderTest.class));
		addTest(new TestSuite(ExtensionsTest.class));
		addTest(new TestSuite(FilterTest.class));
		addTest(WorkingSetTest.suite());
		addTest(new TestSuite(ActivityTest.class));
		addTest(new TestSuite(OpenTest.class));
		addTest(new TestSuite(INavigatorContentServiceTests.class));
		addTest(new TestSuite(ProgrammaticOpenTest.class));
		addTest(new TestSuite(PipelineTest.class));
		addTest(new TestSuite(PipelineChainTest.class));
		addTest(new TestSuite(JstPipelineTest.class));
		addTest(new TestSuite(LabelProviderTest.class));
		addTest(new TestSuite(SorterTest.class));
		addTest(new TestSuite(ViewerTest.class));
		addTest(new TestSuite(CdtTest.class));
		addTest(new TestSuite(M12Tests.class));
		addTest(new TestSuite(FirstClassM1Tests.class));
		addTest(new TestSuite(LinkHelperTest.class));
	}
