
		String renameModeValue = IWorkbenchPreferenceConstants.PROJECT_EXPLORER_RENAME_MODE_INLINE;
		if (!renameModeInline) {
			renameModeValue = IWorkbenchPreferenceConstants.PROJECT_EXPLORER_RENAME_MODE_DIALOG;
		}
		store.setValue(IWorkbenchPreferenceConstants.PROJECT_EXPLORER_RENAME_MODE, renameModeValue);

