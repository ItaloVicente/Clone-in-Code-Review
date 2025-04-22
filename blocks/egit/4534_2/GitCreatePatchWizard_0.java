		setDialogSettings(getOrCreateSection(Activator.getDefault().getDialogSettings(), "GitCreatePatchWizard")); //$NON-NLS-1$
	}

	private static IDialogSettings getOrCreateSection(IDialogSettings settings,
			String sectionName) {
		IDialogSettings section = settings.getSection(sectionName);
		if (section == null)
			section = settings.addNewSection(sectionName);
		return section;
