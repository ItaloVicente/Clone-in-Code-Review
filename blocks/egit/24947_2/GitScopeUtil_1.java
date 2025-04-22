		if (Activator.getDefault().getPreferenceStore()
				.getBoolean(UIPreferences.USE_LOGICAL_MODEL)) {

			try {
				resourcesInScope = findRelatedChanges(part, resources);
			} catch (InvocationTargetException e) {
				Activator.handleError(
						UIText.CommitActionHandler_errorBuildingScope,
						e.getCause(), true);
				resourcesInScope = resources;
			}
		} else {
