public class CssCoreTestSuite extends TestSuite {

	public static Test suite() {
		return new CssCoreTestSuite();
	}

	public CssCoreTestSuite() {
		addTestSuite(CascadeTest.class);
		addTestSuite(FontFaceRulesTest.class);
		addTestSuite(RGBColorImplTest.class);
		addTestSuite(StyleRuleTest.class);
		addTestSuite(ViewCSSTest.class);
		addTestSuite(ValueTest.class);
		addTestSuite(SelectorTest.class);
		addTestSuite(CSSEngineTest.class);
		addTestSuite(ImportTest.class);
	}
