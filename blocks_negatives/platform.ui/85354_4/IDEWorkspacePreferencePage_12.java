        IWorkspaceDescription description = ResourcesPlugin.getWorkspace()
                .getDescription();
        if (autoBuildButton.getSelection() != ResourcesPlugin.getWorkspace()
                .isAutoBuilding()) {
            try {
                description.setAutoBuilding(autoBuildButton.getSelection());
                ResourcesPlugin.getWorkspace().setDescription(description);
            } catch (CoreException e) {
                IDEWorkbenchPlugin.log(
                        "Error changing auto build workspace setting.", e//$NON-NLS-1$
                                .getStatus());
            }
        }

        IPreferenceStore store = getIDEPreferenceStore();

        store.setValue(IDEInternalPreferences.SAVE_ALL_BEFORE_BUILD,
                autoSaveAllButton.getSelection());

        long oldSaveInterval = description.getSnapshotInterval() / 60000;
        long newSaveInterval = Long.parseLong(saveInterval.getStringValue());
        if (oldSaveInterval != newSaveInterval) {
            try {
                description.setSnapshotInterval(newSaveInterval * 60000);
                ResourcesPlugin.getWorkspace().setDescription(description);
                store.firePropertyChangeEvent(IDEInternalPreferences.SAVE_INTERVAL, (int) oldSaveInterval,
                    (int) newSaveInterval);
            } catch (CoreException e) {
                IDEWorkbenchPlugin.log(
                        "Error changing save interval preference", e //$NON-NLS-1$
                                .getStatus());
            }
        }

		store.setValue(IDEInternalPreferences.SHOW_LOCATION, showLocationInWindowTitle.getSelection());

        workspaceName.store();
