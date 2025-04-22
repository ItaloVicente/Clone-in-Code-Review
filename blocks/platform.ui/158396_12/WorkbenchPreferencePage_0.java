		showInlineRenameButton = new Button(composite, SWT.CHECK);
		showInlineRenameButton.setText(WorkbenchMessages.WorkbenchPreference_inlineRename);
		showInlineRenameButton.setToolTipText(WorkbenchMessages.WorkbenchPreference_inlineRename);

		showInlineRenameButton.addSelectionListener(
				widgetSelectedAdapter(e -> renameModeInline = showInlineRenameButton.getSelection()));

		showInlineRenameButton.setSelection(renameModeInline);
