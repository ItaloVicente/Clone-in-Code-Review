public abstract class CSSSWTHelperTestCase extends TestCase {
	protected void registerFontProviderWith(String expectedSymbolicName,
			String family, int size, int style) throws Exception {
		IColorAndFontProvider provider = mock(IColorAndFontProvider.class);
		doReturn(new FontData[] { new FontData(family, size, style) }).when(
				provider).getFont(expectedSymbolicName);
		registerProvider(provider);
