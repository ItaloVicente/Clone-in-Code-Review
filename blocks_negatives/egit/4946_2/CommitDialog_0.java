		IDialogSettings settings = org.eclipse.egit.ui.Activator.getDefault()
				.getDialogSettings();
		if (settings.get(SHOW_UNTRACKED_PREF) != null) {
			showUntracked = Boolean.valueOf(settings.get(SHOW_UNTRACKED_PREF))
					.booleanValue();
		}
