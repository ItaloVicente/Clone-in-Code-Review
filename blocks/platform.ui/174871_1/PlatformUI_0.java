

	public static IDialogSettingsProvider getDialogSettingsProvider(Class<?> clazz) {
		return DialogSettingsProviderService.instance.getProvider(FrameworkUtil.getBundle(clazz));
	}



