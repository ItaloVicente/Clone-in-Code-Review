		if (rememberOptionsButton.getSelection()) {
			IPreferenceStore preferenceStore = Activator
					.getDefault()
					.getPreferenceStore();
			preferenceStore.setValue(FEATURE_FINISH_SQUASH, squashButton.getSelection());
			preferenceStore.setValue(FEATURE_FINISH_KEEP_BRANCH, keepBranchButton.getSelection());
		}

