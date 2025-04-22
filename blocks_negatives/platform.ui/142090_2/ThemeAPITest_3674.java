        assertFalse(themeKeys.contains(BOGUSKEY));
    }

    public void testDataOverride_data1() {
        ITheme theme1 = getTheme1();

        assertEquals(OVERRIDE1, theme1.getString(DATA1));
    }

    public void testDataOverride_data2() {
        ITheme theme1 = getTheme1();

        assertEquals(VALUE2, theme1.getString(DATA2));
    }

    public void testDefaultedColor_rgbcolor() {
        ITheme defaultTheme = getDefaultTheme();
        assertEquals(defaultTheme.getColorRegistry().getRGB(RGBCOLOR),
                defaultTheme.getColorRegistry().getRGB(DEFAULTEDCOLOR));
    }

    public void testDefaultedColor_defaultedcolor() {
        ITheme defaultTheme = getDefaultTheme();
        assertEquals(defaultTheme.getColorRegistry().getRGB(DEFAULTEDCOLOR),
                defaultTheme.getColorRegistry().getRGB(DEFAULTEDCOLOR2));
    }

    public void testDefaultedColor_defaultedcolor2() {
        ITheme defaultTheme = getDefaultTheme();

        assertEquals(defaultTheme.getColorRegistry().getRGB(DEFAULTEDCOLOR2),
                defaultTheme.getColorRegistry().getRGB(DEFAULTEDCOLOR3));
    }

    public void testDefaultedFont_valfont() {
        ITheme defaultTheme = getDefaultTheme();
        assertArrayEquals(
                defaultTheme.getFontRegistry().getFontData(VALFONT),
                defaultTheme.getFontRegistry().getFontData(DEFAULTEDFONT));
    }

    public void testDefaultedFont_defaultedfont() {
        ITheme defaultTheme = getDefaultTheme();
        assertArrayEquals(defaultTheme.getFontRegistry().getFontData(
                DEFAULTEDFONT), defaultTheme.getFontRegistry().getFontData(
                DEFAULTEDFONT2));
    }

    public void testDefaultedFont_defaultedfont2() {
        ITheme defaultTheme = getDefaultTheme();
        assertArrayEquals(defaultTheme.getFontRegistry().getFontData(
                DEFAULTEDFONT2), defaultTheme.getFontRegistry().getFontData(
                DEFAULTEDFONT3));
    }

    public void testDefaultedFontOverride_valfont() {
        ITheme theme1 = getTheme1();
        assertArrayEquals(theme1.getFontRegistry().getFontData(VALFONT),
                theme1.getFontRegistry().getFontData(DEFAULTEDFONT));
    }

    public void testDefaultedFontOverride_defaultedfont2() {
        ITheme theme1 = getTheme1();

        assertArrayEquals(new FontData[] { new FontData("Courier", 16,
                SWT.NORMAL) }, theme1.getFontRegistry().getFontData(
                DEFAULTEDFONT2));
    }

    public void testDefaultedFontOverride_defaultedfont3() {
        ITheme theme1 = getTheme1();

        assertArrayEquals(theme1.getFontRegistry()
                .getFontData(DEFAULTEDFONT2), theme1.getFontRegistry()
                .getFontData(DEFAULTEDFONT3));
    }

    public void testDefaultedOverrideColor_rgbcolor() {
        ITheme theme1 = getTheme1();
        assertEquals(theme1.getColorRegistry().getRGB(RGBCOLOR), theme1
                .getColorRegistry().getRGB(DEFAULTEDCOLOR));
    }

    public void testDefaultedOverrideColor_defaultedcolor2() {
        ITheme theme1 = getTheme1();
        assertEquals(new RGB(9, 9, 9), theme1.getColorRegistry().getRGB(
                DEFAULTEDCOLOR2));

    }

    public void testDefaultedOverrideColor_defaultedcolor3() {
        ITheme theme1 = getTheme1();
        assertEquals(theme1.getColorRegistry().getRGB(DEFAULTEDCOLOR2),
                theme1.getColorRegistry().getRGB(DEFAULTEDCOLOR3));
    }

    public void testFontPreferenceListener_def_novalfont() {
        IPreferenceStore store = PrefUtil.getInternalPreferenceStore();
        ITheme defaultTheme = getDefaultTheme();
        testOverrideFontPreference(defaultTheme, store, NOVALFONT);
    }

    public void testFontPreferenceListener_def_valfont() {
        IPreferenceStore store = PrefUtil.getInternalPreferenceStore();
        ITheme defaultTheme = getDefaultTheme();

        testOverrideFontPreference(defaultTheme, store, VALFONT);
    }

    public void testFontPreferenceListener_def_defaultedfont() {
        IPreferenceStore store = PrefUtil.getInternalPreferenceStore();
        ITheme defaultTheme = getDefaultTheme();

        testOverrideFontPreference(defaultTheme, store, DEFAULTEDFONT);
    }

    public void testFontPreferenceListener_def_nooverridefont() {
        IPreferenceStore store = PrefUtil.getInternalPreferenceStore();
        ITheme defaultTheme = getDefaultTheme();

        testOverrideFontPreference(defaultTheme, store, NOOVERRIDEFONT);
    }

    public void testFontPreferenceListener_th1_valfont() {
        IPreferenceStore store = PrefUtil.getInternalPreferenceStore();
        ITheme theme1 = getTheme1();

        testOverrideFontPreference(theme1, store, VALFONT);
    }

    public void testFontPreferenceListener_th1_novalfont() {
        IPreferenceStore store = PrefUtil.getInternalPreferenceStore();
        ITheme theme1 = getTheme1();

        testOverrideFontPreference(theme1, store, NOVALFONT);
    }

    public void testFontPreferenceListener_th1_defaultedfont() {
        IPreferenceStore store = PrefUtil.getInternalPreferenceStore();
        ITheme theme1 = getTheme1();

        testOverrideFontPreference(theme1, store, DEFAULTEDFONT);
    }

    public void testFontPreferenceListener_th1_nooverridefont() {
        IPreferenceStore store = PrefUtil.getInternalPreferenceStore();
        ITheme theme1 = getTheme1();

        testOverrideFontPreference(theme1, store, NOOVERRIDEFONT);
    }

    public void testGetBadTheme() {
        ITheme badTheme = fManager.getTheme(BOGUSID);
        assertNull(badTheme);
    }

    public void testIntDataConversion() {
        ITheme defaultTheme = getDefaultTheme();
        assertEquals(0, defaultTheme.getInt(DATA1));
        assertEquals(0, defaultTheme.getInt(DATA2));
        assertEquals(0, defaultTheme.getInt(BOOL1));
        assertEquals(0, defaultTheme.getInt(BOGUSKEY));
        assertEquals(3133, defaultTheme.getInt(INT1));
    }

    public void testNoValFont() {
        ITheme defaultTheme = getDefaultTheme();
        assertArrayEquals(defaultTheme.getFontRegistry().defaultFont()
                .getFontData(), defaultTheme.getFontRegistry().getFontData(
                NOVALFONT));
    }

    public void testNoValFontOverride() {
        ITheme theme1 = getTheme1();
        assertArrayEquals(new FontData[] { new FontData("Courier", 10,
                SWT.ITALIC) }, theme1.getFontRegistry()
                .getFontData(NOVALFONT));

    }

    private void testOverrideColorPreference(ITheme theme,
            IPreferenceStore store, String color) {
        RGB oldRGB = theme.getColorRegistry().getRGB(color);
        RGB newRGB = new RGB(75, 21, 68);

        store.setValue(ThemeElementHelper.createPreferenceKey(theme, color),
                StringConverter.asString(newRGB));
        assertEquals(newRGB, theme.getColorRegistry().getRGB(color));
        store
                .setToDefault(ThemeElementHelper.createPreferenceKey(theme,
                        color));
        assertEquals(oldRGB, theme.getColorRegistry().getRGB(color));
    }

    private void testOverrideFontPreference(ITheme theme,
            IPreferenceStore store, String font) {
        FontData[] oldFont = theme.getFontRegistry().getFontData(font);
        FontData[] newFont = new FontData[] { new FontData("Courier", 30,
                SWT.ITALIC) };
        store.setValue(ThemeElementHelper.createPreferenceKey(theme, font),
                PreferenceConverter.getStoredRepresentation(newFont));
        assertArrayEquals(newFont, theme.getFontRegistry().getFontData(font));
        store.setToDefault(ThemeElementHelper.createPreferenceKey(theme, font));
        assertArrayEquals(oldFont, theme.getFontRegistry().getFontData(font));
    }

    public void testPlatformColor() {
        ITheme defaultTheme = getDefaultTheme();
        RGB rgb = null;
        if (Platform.getWS().equals("win32")
                && Platform.getOS().equals("win32")) {
