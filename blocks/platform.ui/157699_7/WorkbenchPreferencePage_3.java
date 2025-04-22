
		String renameModeValue = IWorkbenchPreferenceConstants.RESOURCE_RENAME_MODE_INLINE;
		if (!renameModeInline) {
			renameModeValue = IWorkbenchPreferenceConstants.RESOURCE_RENAME_MODE_DIALOG;
		}
		store.setValue(IWorkbenchPreferenceConstants.RESOURCE_RENAME_MODE, renameModeValue);

