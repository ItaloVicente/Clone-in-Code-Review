		editorReuseThresholdGroup.setLayout(layout);
		editorReuseThresholdGroup.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		reuseEditorsThreshold = new IntegerFieldEditor(IPreferenceConstants.REUSE_EDITORS,
				WorkbenchMessages.WorkbenchPreference_reuseEditorsThreshold, editorReuseThresholdGroup);

		reuseEditorsThreshold.setPreferenceStore(WorkbenchPlugin.getDefault().getPreferenceStore());
		reuseEditorsThreshold.setPage(this);
		reuseEditorsThreshold.setTextLimit(2);
		reuseEditorsThreshold.setErrorMessage(WorkbenchMessages.WorkbenchPreference_reuseEditorsThresholdError);
		reuseEditorsThreshold.setValidateStrategy(StringFieldEditor.VALIDATE_ON_KEY_STROKE);
		reuseEditorsThreshold.setValidRange(1, 99);
		reuseEditorsThreshold.load();
		reuseEditorsThreshold.getLabelControl(editorReuseThresholdGroup).setEnabled(reuseEditors.getSelection());
		reuseEditorsThreshold.getTextControl(editorReuseThresholdGroup).setEnabled(reuseEditors.getSelection());
		reuseEditorsThreshold.setPropertyChangeListener(validityChangeListener);

	}
