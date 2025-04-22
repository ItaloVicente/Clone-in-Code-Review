		if (toggleButton != null)
			Activator
					.getDefault()
					.getPreferenceStore()
					.setValue(UIPreferences.SHOW_PUSH_CONFIRM,
							!toggleButton.getSelection());
