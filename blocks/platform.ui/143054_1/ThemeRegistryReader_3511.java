		desc = (ThemeDescriptor) themeRegistry.findTheme(id);
		if (desc == null) {
			desc = new ThemeDescriptor(id);
			themeRegistry.add(desc);
		}
		desc.extractName(element);

		return desc;
	}

	public void readThemes(IExtensionRegistry in, ThemeRegistry out) {
		setRegistry(out);
		readRegistry(in, PlatformUI.PLUGIN_ID, IWorkbenchRegistryConstants.PL_THEMES);

		readRegistry(in, PlatformUI.PLUGIN_ID, IWorkbenchRegistryConstants.PL_FONT_DEFINITIONS);
	}

	public void setRegistry(ThemeRegistry out) {
		themeRegistry = out;
	}
