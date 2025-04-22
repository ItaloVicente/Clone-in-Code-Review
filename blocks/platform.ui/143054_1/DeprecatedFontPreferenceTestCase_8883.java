		preferenceStore = plugin.getPreferenceStore();

		FontData bogusData = new FontData();
		bogusData.setName("BadData");
		bogusData.setHeight(11);
		FontData[] storedValue = new FontData[2];

		storedValue[0] = bogusData;
		storedValue[1] = (PreferenceConverter.getDefaultFontDataArray(
				preferenceStore, JFaceResources.TEXT_FONT))[0];
		PreferenceConverter
				.setValue(preferenceStore, TEST_FONT_ID, storedValue);
		PreferenceConverter.setDefault(preferenceStore, TEST_FONT_ID,
				storedValue);
	}


	public void testGoodFontDefinition() {

		FontRegistry fontRegistry = JFaceResources.getFontRegistry();
		FontData[] currentTextFonts = PreferenceConverter.getFontDataArray(
				preferenceStore, JFaceResources.TEXT_FONT);
		FontData bestFont = fontRegistry.filterData(currentTextFonts, Display
				.getCurrent())[0];

		assertEquals(bestFont.getName(), currentTextFonts[0].getName());
		assertEquals(bestFont.getHeight(), currentTextFonts[0].getHeight());
	}


	public void testBadFirstFontDefinition() {

		FontRegistry fontRegistry = JFaceResources.getFontRegistry();
		FontData[] currentTestFonts = PreferenceConverter.getFontDataArray(
				preferenceStore, TEST_FONT_ID);
		FontData bestFont = fontRegistry.filterData(currentTestFonts, Display
				.getCurrent())[0];

		assertEquals(bestFont.getName(), currentTestFonts[1].getName());
		assertEquals(bestFont.getHeight(), currentTestFonts[1].getHeight());
	}


	public void testNoFontDefinition() {

		FontRegistry fontRegistry = JFaceResources.getFontRegistry();
		FontData[] currentTestFonts = PreferenceConverter.getFontDataArray(
				preferenceStore, MISSING_FONT_ID);
		FontData bestFont = fontRegistry.filterData(currentTestFonts, Display
				.getCurrent())[0];

		FontData[] systemFontData = Display.getCurrent().getSystemFont()
				.getFontData();

		assertEquals(bestFont.getName(), systemFontData[0].getName());
		assertEquals(bestFont.getHeight(), systemFontData[0].getHeight());
	}
