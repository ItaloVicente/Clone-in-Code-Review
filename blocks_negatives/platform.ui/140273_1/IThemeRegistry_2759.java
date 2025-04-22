        }
    };

    /**
     * Returns the category matching the provided id.
     *
     * @param id the id to search for
     * @return the element matching the provided id, or <code>null</code> if
     * not found
     */
    ThemeElementCategory findCategory(String id);

    /**
     * Returns the color matching the provided id.
     *
     * @param id the id to search for
     * @return the element matching the provided id, or <code>null</code> if
     * not found
     */
    ColorDefinition findColor(String id);

    /**
     * Returns the font matching the provided id.
     *
     * @param id the id to search for
     * @return the element matching the provided id, or <code>null</code> if
     * not found
     */
    FontDefinition findFont(String id);

    /**
     *  Returns the theme matching the provided id.
     *
     * @param id the id to search for
     * @return the element matching the provided id, or <code>null</code> if
     * not found
     */
    IThemeDescriptor findTheme(String id);

    /**
     * Returns a list of categories defined in the registry.
     *
     * @return the categories in this registry
     */
    ThemeElementCategory[] getCategories();

    /**
     * Returns a list of colors defined in the registry.
     *
     * @return the colors in this registry
     */
    ColorDefinition[] getColors();

    /**
     * Returns a list of colors defined for the given theme.  This is the
     * set of base colours overlayed with any theme specific overrides.
     *
     * @param themeId the theme id
     * @return the colors in this theme
     */
    ColorDefinition[] getColorsFor(String themeId);

    /**
     * Returns a list of fonts defined for the given theme.  This is the
     * set of base fonts overlayed with any theme specific overrides.
     *
     * @param themeId the theme id
     * @return the fonts in this theme
     */
    FontDefinition[] getFontsFor(String themeId);

    /**
     * Returns a list of fonts defined in the registry.
     *
     * @return the fonts in this registry
     */
    FontDefinition[] getFonts();

    /**
     * Returns a list of themes defined in the registry.
     *
     * @return the themes in this registry
     */
    IThemeDescriptor[] getThemes();

    /**
     * Return the data map.
     *
     * @return the data map
     */
    Map getData();

    /**
     * Return the set of category presentation bindings.
     *
     * @param category the category to test
     * @return the set of bindings or <code>null</code> if this category has no bindings
     */
    Set getPresentationsBindingsFor(ThemeElementCategory category);
