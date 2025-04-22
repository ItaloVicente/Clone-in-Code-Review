	private void createAutoSavePref(Composite composite) {
		autoSaveButton = new Button(composite, SWT.CHECK);
		autoSaveButton.setText(IDEWorkbenchMessages.IDEWorkspacePreference_autobuild);
		autoSaveButton.setToolTipText(IDEWorkbenchMessages.IDEWorkspacePreference_autobuildToolTip);
		autoSaveButton.setSelection(ResourcesPlugin.getWorkspace().isAutoBuilding());

	}

