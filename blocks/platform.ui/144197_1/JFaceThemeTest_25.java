		ColorRegistry jfaceColors = JFaceResources.getColorRegistry();
		ColorRegistry themeColors = theme.getColorRegistry();
		assertTrue(jfaceColors.getKeySet().containsAll(
				themeColors.getKeySet()));
		for (Object element : themeColors.getKeySet()) {
			String key = (String) element;
			assertEquals(themeColors.getRGB(key), jfaceColors.getRGB(key));
