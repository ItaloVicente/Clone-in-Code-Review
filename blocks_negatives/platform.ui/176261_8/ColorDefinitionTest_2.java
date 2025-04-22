		try {
			new CSSActivator() {
				@Override
				public IColorAndFontProvider getColorAndFontProvider() {
					IColorAndFontProvider provider = mock(IColorAndFontProvider.class);
					doReturn(rgb).when(provider).getColor(symbolicName);
					return provider;
				}
			}.start(null);
		} catch (Exception e) {
			fail("Register color provider should not fail");
		}
