	private IRegistryChangeListener startupRegistryListener = event -> {
		final IExtensionDelta[] deltas = event.getExtensionDeltas(PlatformUI.PLUGIN_ID,
				IWorkbenchRegistryConstants.PL_STARTUP);
		if (deltas.length == 0) {
			return;
		}
		final String disabledPlugins = PrefUtil.getInternalPreferenceStore().getString(
				IPreferenceConstants.PLUGINS_NOT_ACTIVATED_ON_STARTUP);
