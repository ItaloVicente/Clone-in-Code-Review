	/**
	 * Migrate any old internal preferences to the API store.
	 */
	private void migrateInternalPreferences() {

		IPreferenceStore internalStore = WorkbenchPlugin.getDefault()
				.getPreferenceStore();
		IPreferenceStore apiStore = PlatformUI.getPreferenceStore();
		if (internalStore
				.contains(IWorkbenchPreferenceConstants.VIEW_TAB_POSITION)) {
			apiStore.setValue(IWorkbenchPreferenceConstants.VIEW_TAB_POSITION,
					internalStore.getInt(IWorkbenchPreferenceConstants.VIEW_TAB_POSITION));
			internalStore
				.setToDefault(IWorkbenchPreferenceConstants.VIEW_TAB_POSITION);
		}

		if (internalStore
				.contains(IWorkbenchPreferenceConstants.EDITOR_TAB_POSITION)) {

			apiStore.setValue(
					IWorkbenchPreferenceConstants.EDITOR_TAB_POSITION,
					internalStore.getInt(IWorkbenchPreferenceConstants.EDITOR_TAB_POSITION));
			internalStore
				.setToDefault(IWorkbenchPreferenceConstants.EDITOR_TAB_POSITION);
		}


		if (internalStore
				.contains(IWorkbenchPreferenceConstants.SHOW_MULTIPLE_EDITOR_TABS)) {
			apiStore
					.setValue(
							IWorkbenchPreferenceConstants.SHOW_MULTIPLE_EDITOR_TABS,
							internalStore
							.getBoolean(IWorkbenchPreferenceConstants.SHOW_MULTIPLE_EDITOR_TABS));
			internalStore
					.setToDefault(IWorkbenchPreferenceConstants.SHOW_MULTIPLE_EDITOR_TABS);
		}
	}
