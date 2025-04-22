    public void testFontCascadeEvents() {
        ITheme currentTheme = fManager.getCurrentTheme();
        assertNotNull(currentTheme);

        ThemePropertyListener managerListener = new ThemePropertyListener();
        ThemePropertyListener themeListener = new ThemePropertyListener();
        fManager.addPropertyChangeListener(managerListener);
        currentTheme.addPropertyChangeListener(themeListener);

        FontRegistry fontRegistry = currentTheme.getFontRegistry();
        FontData[] oldFont = fontRegistry.getFontData(VALFONT);
        FontData[] newFont = new FontData[] { new FontData("Courier", 30,
                SWT.ITALIC) };
        fontRegistry.put(VALFONT, newFont);
        fontRegistry.put(VALFONT, oldFont);

        checkEvents(managerListener, fontRegistry, oldFont, newFont);
        checkEvents(themeListener, fontRegistry, oldFont, newFont);

        fManager.removePropertyChangeListener(managerListener);
        currentTheme.removePropertyChangeListener(themeListener);
    }

