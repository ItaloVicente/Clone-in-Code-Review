		if (autoBuildButton.getSelection() != getWorkspace().isAutoBuilding()) {
            try {
                description.setAutoBuilding(autoBuildButton.getSelection());
				getWorkspace().setDescription(description);
            } catch (CoreException e) {
                IDEWorkbenchPlugin.log(
                        "Error changing auto build workspace setting.", e//$NON-NLS-1$
                                .getStatus());
            }
        }
		if (maxSimultaneousBuilds.getIntValue() != description.getMaxConcurrentBuilds()) {
			try {
				description.setMaxConcurrentBuilds(maxSimultaneousBuilds.getIntValue());
				getWorkspace().setDescription(description);
			} catch (CoreException e) {
				IDEWorkbenchPlugin.log("Error changing max cucrrent builds workspace setting.", e//$NON-NLS-1$
						.getStatus());
			}
		}

        IPreferenceStore store = getIDEPreferenceStore();

        store.setValue(IDEInternalPreferences.SAVE_ALL_BEFORE_BUILD,
                autoSaveAllButton.getSelection());

