	
	public void testColorOverride() {
		FontRegistry jfaceFonts = JFaceResources.getFontRegistry();
		FontData[] originalData = jfaceFonts.getFontData(FONT_ID);
		assertEquals(1, originalData.length);
		assertEquals("Sans", originalData[0].name);
		fThemeEngine.setTheme(THEME_WITH_FONT_OVERRIDE, false);
		FontData[] changedData = jfaceFonts.getFontData(FONT_ID);
		assertEquals(1, changedData.length);
		assertEquals("Sans", changedData[0].name);
	}
