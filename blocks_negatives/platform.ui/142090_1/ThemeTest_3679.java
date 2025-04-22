    /**
     * @return
     */
    protected ITheme getDefaultTheme() {
        ITheme defaultTheme = fManager.getTheme(IThemeManager.DEFAULT_THEME);
        assertNotNull(defaultTheme);
        return defaultTheme;
    }
