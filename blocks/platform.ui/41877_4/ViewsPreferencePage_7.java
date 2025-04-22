		}
		IPreferenceStore apiStore = PrefUtil.getAPIPreferenceStore();
		enableAnimations.setSelection(apiStore.getDefaultBoolean(IWorkbenchPreferenceConstants.ENABLE_ANIMATIONS));
		useColoredLabels.setSelection(apiStore.getDefaultBoolean(IWorkbenchPreferenceConstants.USE_COLORED_LABELS));
		if (enableMru != null) {
			enableMru.setSelection(getDefaultMRUValue());
