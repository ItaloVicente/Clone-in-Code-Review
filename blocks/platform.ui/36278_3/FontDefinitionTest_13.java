	private void registerFontProviderWith(final String symbolicName, final FontData fontData) {
		try {
			new CSSActivator() {
				@Override
				public IColorAndFontProvider getColorAndFontProvider() {
					IColorAndFontProvider provider = mock(IColorAndFontProvider.class);
					doReturn(new FontData[] { fontData }).when(provider).getFont(symbolicName);
					return provider;
				};
			}.start(null);
		} catch (Exception e) {
			fail("CssActivator start failed");
		}
