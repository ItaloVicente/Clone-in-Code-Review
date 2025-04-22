		showInlineRenameButton = new Button(composite, SWT.CHECK);
		showInlineRenameButton.setText(WorkbenchMessages.WorkbenchPreference_inlineRename);
		showInlineRenameButton.setToolTipText(WorkbenchMessages.WorkbenchPreference_inlineRename);

		showInlineRenameButton.setSelection(
				getPreferenceStore().getBoolean(IPreferenceConstants.RESOURCE_INLINE_RENAME));
