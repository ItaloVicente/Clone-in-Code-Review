		IPreferenceStore apiStore = PrefUtil.getAPIPreferenceStore();
		apiStore.setValue(IWorkbenchPreferenceConstants.ENABLE_ANIMATIONS,
				enableAnimations.getSelection());
		apiStore.setValue(IWorkbenchPreferenceConstants.USE_COLORED_LABELS,
				useColoredLabels.getSelection());
		((PreferencePageEnhancer) Tweaklets.get(PreferencePageEnhancer.KEY)).performOK();
