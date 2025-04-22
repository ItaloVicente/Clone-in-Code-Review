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
						"PreferenceDialog.saveErrorMessage", //$NON-NLS-1$
						new Object[] { getTitle(), e.getMessage() });
				Policy.getStatusHandler().show(
						new Status(IStatus.ERROR, Policy.JFACE, message, e),
						JFaceResources
			}
		}
		return isOk;
