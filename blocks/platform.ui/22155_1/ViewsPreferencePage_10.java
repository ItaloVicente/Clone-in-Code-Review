
		return result;
	}

	private Map<String, String> createThemeAssociations() {
		Map<String, String> result = new HashMap<String, String>();
		IExtensionRegistry registry = RegistryFactory.getRegistry();
		IExtensionPoint extPoint = registry.getExtensionPoint("org.eclipse.e4.ui.css.swt.theme"); //$NON-NLS-1$

		for (IExtension e : extPoint.getExtensions()) {
			for (IConfigurationElement ce : e.getConfigurationElements()) {
				if (ce.getName().equals("themeassociation")) { //$NON-NLS-1$
					String themeId = ce.getAttribute("themeid"); //$NON-NLS-1$
					String osVersion = ce.getAttribute("os_version"); //$NON-NLS-1$
					result.put(osVersion != null ? themeId + osVersion : themeId,
							ce.getAttribute("colorsandfonts")); //$NON-NLS-1$						
				}
			}
