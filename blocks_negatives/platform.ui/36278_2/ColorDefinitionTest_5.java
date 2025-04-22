	private void registerColorProviderWith(final String symbolicName, final RGB rgb) throws Exception {
		new CSSActivator() {
			@Override
			public IColorAndFontProvider getColorAndFontProvider() {
				IColorAndFontProvider provider = mock(IColorAndFontProvider.class);
				doReturn(rgb).when(provider).getColor(symbolicName);
				return provider;
			};
		}.start(null);
