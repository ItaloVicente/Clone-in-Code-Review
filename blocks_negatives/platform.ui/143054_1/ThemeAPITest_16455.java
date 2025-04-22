        assertArrayEquals(data, defaultTheme.getFontRegistry().getFontData(
                PLATFORMFONT));
    }

    public void testRGBColor() {
        ITheme defaultTheme = getDefaultTheme();
        assertEquals(new RGB(1, 1, 2), defaultTheme.getColorRegistry().getRGB(
                RGBCOLOR));
    }

    public void testRGBColorOverride() {
        ITheme theme1 = getTheme1();
        assertEquals(new RGB(2, 1, 1), theme1.getColorRegistry().getRGB(
                RGBCOLOR));
    }

    public void testSetTheme() {
        ThemePropertyListener listener = new ThemePropertyListener();
        fManager.addPropertyChangeListener(listener);
        ITheme currentTheme = fManager.getCurrentTheme();
        fManager.setCurrentTheme(BOGUSID);
        assertEquals(currentTheme, fManager.getCurrentTheme());
        fManager.setCurrentTheme(THEME1);
        assertNotSame(currentTheme, fManager.getCurrentTheme());
        ITheme newCurrentTheme = fManager.getCurrentTheme();
        ITheme theme1 = getTheme1();
        assertEquals(theme1, newCurrentTheme);
