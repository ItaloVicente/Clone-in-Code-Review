	private void registerProvider(final IColorAndFontProvider provider) {
		try {
			new CSSActivator() {
				@Override
				public IColorAndFontProvider getColorAndFontProvider() {
					return provider;
				};
			}.start(null);
		} catch (Exception e) {
			fail();
		}

