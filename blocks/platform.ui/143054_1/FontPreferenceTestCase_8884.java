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
		FontData[] bestFont = fontRegistry.bestDataArray(currentTextFonts,
				Display.getCurrent());

		assertEquals(bestFont[0].getName(), currentTextFonts[0].getName());
		assertEquals(bestFont[0].getHeight(), currentTextFonts[0].getHeight());
	}


	public void testBadFirstFontDefinition() {

		FontRegistry fontRegistry = JFaceResources.getFontRegistry();
		FontData[] currentTestFonts = PreferenceConverter.getFontDataArray(
				preferenceStore, TEST_FONT_ID);
		FontData[] bestFont = fontRegistry.filterData(currentTestFonts,
				Display.getCurrent());

		assertEquals(bestFont[0].getName(), currentTestFonts[1].getName());
		assertEquals(bestFont[0].getHeight(), currentTestFonts[1].getHeight());
	}


	public void testNoFontDefinition() {

		FontRegistry fontRegistry = JFaceResources.getFontRegistry();
		FontData[] currentTestFonts = PreferenceConverter.getFontDataArray(
				preferenceStore, MISSING_FONT_ID);
		FontData[] bestFont = fontRegistry.filterData(currentTestFonts,
				Display.getCurrent());

		FontData[] systemFontData = Display.getCurrent().getSystemFont()
				.getFontData();

		assertEquals(bestFont[0].getName(), systemFontData[0].getName());
		assertEquals(bestFont[0].getHeight(), systemFontData[0].getHeight());
	}

	public void testNonUIThreadFontAccess() {
