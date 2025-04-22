
	protected void registerColorProviderWith(String expectedSymbolicName,
			RGB rgb) throws Exception {
		IColorAndFontProvider provider = mock(IColorAndFontProvider.class);
		doReturn(rgb).when(provider).getColor(expectedSymbolicName);
		registerProvider(provider);
