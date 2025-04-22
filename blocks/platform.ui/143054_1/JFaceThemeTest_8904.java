	private void setAndTest(String themeId, IPropertyChangeListener listener) {
		JFaceResources.getFontRegistry().addListener(listener);
		JFaceResources.getColorRegistry().addListener(listener);
		fManager.setCurrentTheme(themeId);
		ITheme theme = fManager.getTheme(themeId);
		assertEquals(theme, fManager.getCurrentTheme());
		{
			FontRegistry jfaceFonts = JFaceResources.getFontRegistry();
			FontRegistry themeFonts = theme.getFontRegistry();
			assertTrue(jfaceFonts.getKeySet().containsAll(
					themeFonts.getKeySet()));
			for (Object element : themeFonts.getKeySet()) {
				String key = (String) element;
				assertArrayEquals(themeFonts.getFontData(key), jfaceFonts
						.getFontData(key));
			}
		}
		{
			ColorRegistry jfaceColors = JFaceResources.getColorRegistry();
			ColorRegistry themeColors = theme.getColorRegistry();
			assertTrue(jfaceColors.getKeySet().containsAll(
					themeColors.getKeySet()));
			for (Object element : themeColors.getKeySet()) {
				String key = (String) element;
				assertEquals(themeColors.getRGB(key), jfaceColors.getRGB(key));
			}
		}
		JFaceResources.getFontRegistry().removeListener(listener);
		JFaceResources.getColorRegistry().removeListener(listener);
	}
