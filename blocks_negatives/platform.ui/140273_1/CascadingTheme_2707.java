    /**
     * @param colorRegistry
     * @param fontRegistry
     */
    public CascadingTheme(ITheme currentTheme,
            CascadingColorRegistry colorRegistry,
            CascadingFontRegistry fontRegistry) {
        this.currentTheme = currentTheme;
        this.colorRegistry = colorRegistry;
        this.fontRegistry = fontRegistry;
