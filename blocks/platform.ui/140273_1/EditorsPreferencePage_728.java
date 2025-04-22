		IPreferenceStore store = getPreferenceStore();
		getAPIPreferenceStore().setValue(IWorkbenchPreferenceConstants.SHOW_MULTIPLE_EDITOR_TABS,
				showMultipleEditorTabs.getSelection());
		getAPIPreferenceStore().setValue(IWorkbenchPreferenceConstants.DISABLE_OPEN_EDITOR_IN_PLACE,
				!allowInplaceEditor.getSelection());
		store.setValue(IPreferenceConstants.USE_IPERSISTABLE_EDITORS, useIPersistableEditor.getSelection());
		getAPIPreferenceStore().setValue(IWorkbenchPreferenceConstants.PROMPT_WHEN_SAVEABLE_STILL_OPEN,
				promptWhenStillOpenEditor.getSelection());

		store.setValue(IPreferenceConstants.REUSE_EDITORS_BOOLEAN, reuseEditors.getSelection());
		reuseEditorsThreshold.store();

		recentFilesEditor.store();

		PrefUtil.savePrefs();
		return super.performOk();
	}

	@Override
