	public static boolean getRetarget(IConfigurationElement element) {
		String r = element.getAttribute(IWorkbenchRegistryConstants.ATT_RETARGET);
		return Boolean.parseBoolean(r);
	}

	public static String getDefinitionId(IConfigurationElement element) {
		return element.getAttribute(IWorkbenchRegistryConstants.ATT_DEFINITION_ID);
	}

