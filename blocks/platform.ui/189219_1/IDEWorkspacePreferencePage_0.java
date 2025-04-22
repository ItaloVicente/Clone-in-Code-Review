	private void createExcludeBuildFoldersControls(Composite parent) {

		this.excludeBuildFoldersButton = new Button(parent, SWT.CHECK);
		this.excludeBuildFoldersButton
				.setText(IDEWorkbenchMessages.IDEWorkspacePreference_excludeBuildFoldersButton_Text);
		this.excludeBuildFoldersButton
				.setToolTipText(IDEWorkbenchMessages.IDEWorkspacePreference_excludeBuildFoldersButton_ToolTip);

		boolean excludeBuildFolders = Platform.getPreferencesService().getBoolean(ResourcesPlugin.PI_RESOURCES,
				ResourcesPlugin.PREF_excludeBuildFolders, false, null);

		this.excludeBuildFoldersButton.setSelection(excludeBuildFolders);
	}

