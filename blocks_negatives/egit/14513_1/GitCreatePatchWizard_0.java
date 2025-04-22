		setDialogSettings(getOrCreateSection(Activator.getDefault().getDialogSettings(), "GitCreatePatchWizard")); //$NON-NLS-1$
	}

	/*
	 * Copy of org.eclipse.jface.dialogs.DialogSettings.getOrCreateSection(IDialogSettings, String)
	 * which is not available in 3.5.
	 */
	private static IDialogSettings getOrCreateSection(IDialogSettings settings,
			String sectionName) {
		IDialogSettings section = settings.getSection(sectionName);
		if (section == null)
			section = settings.addNewSection(sectionName);
		return section;
