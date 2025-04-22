		IConfigurationElement element = getConfigurationElement();
		if (!element.isValid()) {
			crashDisable();
			return null;
		}
		return element.getContributor().getName();
