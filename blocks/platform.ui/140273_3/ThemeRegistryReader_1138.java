		String value = element.getAttribute(IWorkbenchRegistryConstants.ATT_VALUE);
		if (value == null) {
			value = checkColorFactory(element);
		}
		return value;
	}

	private String getPlatformSpecificColorValue(IConfigurationElement[] elements) {
		return getColorValue(getBestPlatformMatch(elements));
	}

	private IConfigurationElement getBestPlatformMatch(IConfigurationElement[] elements) {
		IConfigurationElement match = null;

		String osname = Platform.getOS();
		String wsname = Platform.getWS();

		for (IConfigurationElement element : elements) {
			String elementOs = element.getAttribute(IWorkbenchRegistryConstants.ATT_OS);
			String elementWs = element.getAttribute(IWorkbenchRegistryConstants.ATT_WS);

			if (osname.equalsIgnoreCase(elementOs)) {
				if (wsname.equalsIgnoreCase(elementWs)) {
					return element;
				}
				match = element;
			} else if (wsname.equalsIgnoreCase(elementWs)) {
				match = element;
			}
		}

		return match;
	}

	@Override
