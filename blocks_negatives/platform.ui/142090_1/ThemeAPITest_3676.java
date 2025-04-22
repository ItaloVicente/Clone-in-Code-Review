        assertEquals(IThemeManager.CHANGE_CURRENT_THEME, event.getProperty());
        assertEquals(currentTheme, event.getOldValue());
        assertEquals(newCurrentTheme, event.getNewValue());
        fManager.removePropertyChangeListener(listener);
    }

    public void testStringData() {
        ITheme defaultTheme = getDefaultTheme();
        assertEquals("value1", defaultTheme.getString(DATA1));
        assertEquals(VALUE2, defaultTheme.getString(DATA2));
        assertEquals("3133", defaultTheme.getString(INT1));
        assertEquals("true", defaultTheme.getString(BOOL1));
        assertEquals(null, defaultTheme.getString(BOGUSKEY));
    }

    public void testSWTColor() {
        ITheme defaultTheme = getDefaultTheme();
        assertEquals(Display.getDefault().getSystemColor(SWT.COLOR_DARK_BLUE)
                .getRGB(), defaultTheme.getColorRegistry().getRGB(SWTCOLOR));
    }

    public void testSWTColorOverride() {
        ITheme theme1 = getTheme1();
        assertEquals(Display.getDefault().getSystemColor(SWT.COLOR_DARK_GREEN)
                .getRGB(), theme1.getColorRegistry().getRGB(SWTCOLOR));
    }

    public void testThemeDescription_default() {
        ITheme defaultTheme = getDefaultTheme();
        assertEquals(IThemeManager.DEFAULT_THEME, defaultTheme.getId());
        assertNotNull(defaultTheme.getLabel());
    }

    public void testThemeDescription_theme1() {
        ITheme theme1 = getTheme1();
        assertEquals(THEME1, theme1.getId());
        assertEquals("test theme 1", theme1.getLabel());
    }

    public void testValFont() {
        ITheme defaultTheme = getDefaultTheme();
        assertArrayEquals(
                new FontData[] { new FontData("Tahoma", 20, SWT.BOLD) },
                defaultTheme.getFontRegistry().getFontData(VALFONT));
    }

    /*
     * The following tests check to ensure that support for multiple extensions
     * contributing to the same theeme work. They also check to ensure that the
     * first value encountered for a given font/colour is the only one used.
     */

    public void testThemeExtensionName() {
        ITheme ext1 = fManager.getTheme(EXTENDED_THEME1);
        ITheme ext2 = fManager.getTheme(EXTENDED_THEME2);
        ITheme ext3 = fManager.getTheme(EXTENDED_THEME3);

        assertEquals("Extended Theme 1", ext1.getLabel());
        assertEquals("Extended Theme 2", ext2.getLabel());
        assertEquals("Extended Theme 3", ext3.getLabel());
    }

    public void testThemeExtensionData() {
        ITheme ext1 = fManager.getTheme(EXTENDED_THEME1);
        assertNotNull(ext1.getString("d1"));
        assertEquals("d1", ext1.getString("d1"));
        assertNotNull(ext1.getString("d2"));
    }

    public void testThemeExtensionColor() {
        ITheme ext1 = fManager.getTheme(EXTENDED_THEME1);
        assertEquals(Display.getDefault().getSystemColor(SWT.COLOR_RED)
                .getRGB(), ext1.getColorRegistry().getRGB(SWTCOLOR));

        assertEquals(Display.getDefault().getSystemColor(SWT.COLOR_RED)
                .getRGB(), ext1.getColorRegistry().getRGB(RGBCOLOR));
    }

    public void testThemeExtensionFont() {
        ITheme ext1 = fManager.getTheme(EXTENDED_THEME1);

        FontData[] fd = new FontData[] { new FontData("Sans", 10,
                SWT.NORMAL) };

        assertArrayEquals(fd, ext1.getFontRegistry()
                .getFontData(VALFONT));

        assertArrayEquals(fd, ext1.getFontRegistry()
                .getFontData(NOVALFONT));
    }

    /**
