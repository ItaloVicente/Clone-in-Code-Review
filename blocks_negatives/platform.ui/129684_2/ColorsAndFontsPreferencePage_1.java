                    }
                }
            }
            {
                ColorDefinition[] colorDefinitions = themeRegistry
                        .getColorsFor(currentTheme.getId());
                for (int i = 0; i < colorDefinitions.length; i++) {
                    if (!colorDefinitions[i].isEditable()) {
