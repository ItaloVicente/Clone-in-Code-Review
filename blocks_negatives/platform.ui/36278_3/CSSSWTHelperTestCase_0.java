	private void registerProvider(final IColorAndFontProvider provider) throws Exception {
		new CSSActivator() {
			@Override
			public IColorAndFontProvider getColorAndFontProvider() {
				return provider;
			};
		}.start(null);
