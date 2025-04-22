        IThemeDescriptor desc = findTheme(themeId);
        FontDefinition[] overrides = desc.getFonts();
        return (FontDefinition[]) overlay(defs, overrides);
    }

    /**
     * Overlay the overrides onto the base definitions.
     *
     * @param defs the base definitions
     * @param overrides the overrides
     * @return the overlayed elements
     */
    private IThemeElementDefinition[] overlay(IThemeElementDefinition[] defs,
            IThemeElementDefinition[] overrides) {
        for (IThemeElementDefinition override : overrides) {
