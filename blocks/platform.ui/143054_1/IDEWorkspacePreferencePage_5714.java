		IWorkspaceDescription description = ResourcesPlugin.getWorkspace()
				.getDescription();
		IPreferenceStore store = getIDEPreferenceStore();

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
