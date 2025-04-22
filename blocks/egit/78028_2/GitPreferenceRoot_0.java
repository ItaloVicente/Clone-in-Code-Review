		corePreferences = new ScopedPreferenceStore(InstanceScope.INSTANCE,
				org.eclipse.egit.core.Activator.getPluginId());
	}

	@Override
	public void dispose() {
		super.dispose();
		corePreferences = null;
	}

	@Override
	public boolean performOk() {
		boolean isOk = super.performOk();
		if (isOk && corePreferences.needsSaving()) {
			try {
				corePreferences.save();
			} catch (IOException e) {
				String message = JFaceResources.format(
						"PreferenceDialog.saveErrorMessage", getTitle(), //$NON-NLS-1$
						e.getMessage());
				Policy.getStatusHandler().show(
						new Status(IStatus.ERROR, Policy.JFACE, message, e),
						JFaceResources
								.getString("PreferenceDialog.saveErrorTitle")); //$NON-NLS-1$
			}
		}
		return isOk;
