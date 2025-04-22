            }
        }
        return set;
    }

    /**
     * Installs the given color in the preference store and optionally the color
     * registry.
     *
     * @param definition
     *            the color definition
     * @param theme
     *            the theme defining the color
     * @param store
     *            the preference store from which to set and obtain color data
     * @param setInRegistry
     * 			  whether the color should be put into the registry
     */

    private static void installColor(ColorDefinition definition, ITheme theme,
            IPreferenceStore store, boolean setInRegistry) {


    	ColorRegistry registry = theme.getColorRegistry();

        String id = definition.getId();
        String key = createPreferenceKey(theme, id);
        RGB prefColor = store != null
        	? PreferenceConverter.getColor(store, key)
        	: null;
