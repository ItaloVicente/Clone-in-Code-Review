	private void restoreInput() {
		IPreferenceStore prefStore = Activator
				.getDefault()
				.getPreferenceStore();
		squashButton.setSelection(prefStore.getBoolean(FEATURE_FINISH_SQUASH));
		keepBranchButton.setSelection(prefStore.getBoolean(FEATURE_FINISH_KEEP_BRANCH));
	}

