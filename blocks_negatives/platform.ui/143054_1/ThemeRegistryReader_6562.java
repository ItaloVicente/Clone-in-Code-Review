        desc = (ThemeDescriptor) themeRegistry.findTheme(id);
        if (desc == null) {
            desc = new ThemeDescriptor(id);
            themeRegistry.add(desc);
        }
        desc.extractName(element);

        return desc;
    }

    /**
     * Read the theme extensions within a registry.
     *
     * @param in the registry to read
     * @param out the registry to write to
     */
    public void readThemes(IExtensionRegistry in, ThemeRegistry out) {
        setRegistry(out);
        readRegistry(in, PlatformUI.PLUGIN_ID, IWorkbenchRegistryConstants.PL_THEMES);

        readRegistry(in, PlatformUI.PLUGIN_ID,
                IWorkbenchRegistryConstants.PL_FONT_DEFINITIONS);
    }

    /**
     * Set the output registry.
     *
     * @param out the output registry
     */
    public void setRegistry(ThemeRegistry out) {
        themeRegistry = out;
    }
