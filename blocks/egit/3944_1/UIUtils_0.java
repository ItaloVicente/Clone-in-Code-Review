
	public static IDialogSettings getDialogBoundSettings(final Class<?> clazz) {
		return getDialogSettings(clazz.getName() + ".dialogBounds"); //$NON-NLS-1$
	}

	public static IDialogSettings getDialogSettings(final String sectionName) {
		IDialogSettings settings = Activator.getDefault().getDialogSettings();
		IDialogSettings section = settings.getSection(sectionName);
		if (section == null)
			section = settings.addNewSection(sectionName);
		return section;
	}
