	@Override
	public boolean performOk() {
		boolean isOk = super.performOk();
		if (isOk) {
			IPreferenceStore uiPreferences = org.eclipse.egit.ui.Activator
					.getDefault().getPreferenceStore();
			if (uiPreferences.needsSaving()
					&& (uiPreferences instanceof IPersistentPreferenceStore)) {
				try {
					((IPersistentPreferenceStore) uiPreferences).save();
				} catch (IOException e) {
					String message = JFaceResources.format(
							"PreferenceDialog.saveErrorMessage", getTitle(), //$NON-NLS-1$
							e.getMessage());
					Policy.getStatusHandler().show(
							new Status(IStatus.ERROR, Policy.JFACE, message, e),
							JFaceResources.getString(
									"PreferenceDialog.saveErrorTitle")); //$NON-NLS-1$
				}
			}
		}
		return isOk;
	}

