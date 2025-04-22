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

    /**
     * Test for a valid font like the test font. The first good one
     * we should find should be the first one in the list.
     */

    public void testGoodFontDefinition() {

        FontRegistry fontRegistry = JFaceResources.getFontRegistry();
        FontData[] currentTextFonts = PreferenceConverter.getFontDataArray(
                preferenceStore, JFaceResources.TEXT_FONT);
        FontData bestFont = fontRegistry.filterData(currentTextFonts, Display
                .getCurrent())[0];

        assertEquals(bestFont.getName(), currentTextFonts[0].getName());
        assertEquals(bestFont.getHeight(), currentTextFonts[0].getHeight());
    }

    /**
     * Test that if the first font in the list is bad that the
     * second one comes back as valid.
     */

    public void testBadFirstFontDefinition() {

        FontRegistry fontRegistry = JFaceResources.getFontRegistry();
        FontData[] currentTestFonts = PreferenceConverter.getFontDataArray(
                preferenceStore, TEST_FONT_ID);
        FontData bestFont = fontRegistry.filterData(currentTestFonts, Display
                .getCurrent())[0];

        assertEquals(bestFont.getName(), currentTestFonts[1].getName());
        assertEquals(bestFont.getHeight(), currentTestFonts[1].getHeight());
    }

    /**
     * Test that the no valid font is returned when the entry
     * is missing.
     */

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
