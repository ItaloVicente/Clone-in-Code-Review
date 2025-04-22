	private Map<String, String> createThemeAssociations() {
		Map<String, String> result = new HashMap<String, String>();
		IExtensionRegistry registry = RegistryFactory.getRegistry();
		IExtensionPoint extPoint = registry.getExtensionPoint(E4_THEME_EXTENSION_POINT);

		for (IExtension e : extPoint.getExtensions()) {
			for (IConfigurationElement ce : e.getConfigurationElements()) {
				if (ce.getName().equals(ATT_THEME_ASSOCIATION)) {
					String themeId = ce.getAttribute(ATT_THEME_ID);
					String osVersion = ce.getAttribute(ATT_OS_VERSION);
					result.put(osVersion != null ? themeId + osVersion : themeId,
							ce.getAttribute(ATT_COLOR_AND_FONT_ID));
				}
			}
		}
		return result;
	}

	private List<ColorsAndFontsTheme> getColorsAndFontsThemes() {
		List<ColorsAndFontsTheme> result = new ArrayList<ColorsAndFontsTheme>();
