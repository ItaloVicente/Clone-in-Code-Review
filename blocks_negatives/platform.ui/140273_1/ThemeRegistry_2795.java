            if (idx >= 0) {
                defs[idx] = overlay(defs[idx], override);
            }
        }
        return defs;
    }

    /**
     * Overlay the override onto the base definition.
     *
     * @param defs the base definition
     * @param overrides the override
     * @return the overlayed element
     */
    private IThemeElementDefinition overlay(IThemeElementDefinition original,
            IThemeElementDefinition overlay) {
        if (original instanceof ColorDefinition) {
            ColorDefinition originalColor = (ColorDefinition) original;
            ColorDefinition overlayColor = (ColorDefinition) overlay;
            return new ColorDefinition(originalColor, overlayColor.getValue());
        } else if (original instanceof FontDefinition) {
            FontDefinition originalFont = (FontDefinition) original;
            FontDefinition overlayFont = (FontDefinition) overlay;
            return new FontDefinition(originalFont, overlayFont.getValue());
        }
        return null;
    }

    /**
     * @param definition
     */
    void add(FontDefinition definition) {
        if (findFont(definition.getId()) != null) {
