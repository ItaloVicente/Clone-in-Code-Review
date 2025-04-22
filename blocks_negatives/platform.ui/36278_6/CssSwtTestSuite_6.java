public class CssSwtTestSuite extends TestSuite {
	/**
	 * Returns the suite. This is required to use the JUnit Launcher.
	 */
	public static final Test suite() {
		return new CssSwtTestSuite();
	}

	/**
	 * Construct the test suite.
	 */
	public CssSwtTestSuite() {
		addTestSuite(CSSSWTFontHelperTest.class);
		addTestSuite(CSSSWTColorHelperTest.class);
		addTestSuite(CSSResourcesHelpersTest.class);
		addTestSuite(SWTResourceRegistryKeyFactoryTest.class);
		addTestSuite(SWTResourcesRegistryTest.class);
		addTestSuite(FontDefinitionTest.class);
		addTestSuite(ColorDefinitionTest.class);
		addTestSuite(ThemesExtensionTest.class);
		addTestSuite(IEclipsePreferencesTest.class);
		addTestSuite(EclipsePreferencesHelperTest.class);
		addTestSuite(CSSSWTWidgetTest.class);
		addTestSuite(LabelTest.class);
		addTestSuite(CTabFolderTest.class);
		addTestSuite(CTabItemTest.class);
		addTestSuite(IdClassLabelColorTest.class);
		addTestSuite(ShellTest.class);
		addTestSuite(ButtonTest.class);
		addTestSuite(GradientTest.class);
		addTestSuite(MarginTest.class);
		addTestSuite(InnerClassElementTest.class);
		addTestSuite(EclipsePreferencesHandlerTest.class);
		addTestSuite(PreferenceOverriddenByCssChangeListenerTest.class);

		addTestSuite(ButtonTextTransformTest.class);
		addTestSuite(LabelTextTransformTest.class);
		addTestSuite(TextTextTransformTest.class);

		addTestSuite(DescendentTest.class);
		addTestSuite(InheritTest.class);
