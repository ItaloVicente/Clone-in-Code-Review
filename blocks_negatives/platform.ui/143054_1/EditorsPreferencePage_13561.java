        IPreferenceStore store = getPreferenceStore();
        showMultipleEditorTabs
				.setSelection(getAPIPreferenceStore()
						.getDefaultBoolean(IWorkbenchPreferenceConstants.SHOW_MULTIPLE_EDITOR_TABS));
        allowInplaceEditor
        		.setSelection(!getAPIPreferenceStore()
        				.getDefaultBoolean(IWorkbenchPreferenceConstants.DISABLE_OPEN_EDITOR_IN_PLACE));
		useIPersistableEditor
				.setSelection(store
						.getDefaultBoolean(IPreferenceConstants.USE_IPERSISTABLE_EDITORS));
		promptWhenStillOpenEditor
				.setSelection(getAPIPreferenceStore()
						.getDefaultBoolean(IWorkbenchPreferenceConstants.PROMPT_WHEN_SAVEABLE_STILL_OPEN));
        reuseEditors.setSelection(store
                .getDefaultBoolean(IPreferenceConstants.REUSE_EDITORS_BOOLEAN));
        reuseEditorsThreshold.loadDefault();
        reuseEditorsThreshold.getLabelControl(editorReuseThresholdGroup)
                .setEnabled(reuseEditors.getSelection());
        reuseEditorsThreshold.getTextControl(editorReuseThresholdGroup)
                .setEnabled(reuseEditors.getSelection());
        recentFilesEditor.loadDefault();
    }

    @Override
