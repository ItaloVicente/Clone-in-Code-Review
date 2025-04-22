		if (newWindow) {
			page.fireInitialPartVisibilityEvents();
		} else {
			page.updatePerspectiveActionSets();
		}
		partService.setPage(page);
		updateActionSets();

		IPreferenceStore preferenceStore = PrefUtil.getAPIPreferenceStore();
		boolean enableAnimations = preferenceStore
				.getBoolean(IWorkbenchPreferenceConstants.ENABLE_ANIMATIONS);
		preferenceStore.setValue(IWorkbenchPreferenceConstants.ENABLE_ANIMATIONS, false);
