		}
	};

	public AdaptedResourceNavigator() {
		IDialogSettings workbenchSettings = getPlugin().getDialogSettings();
		settings = workbenchSettings.getSection("ResourceNavigator"); //$NON-NLS-1$
		if (settings == null)
