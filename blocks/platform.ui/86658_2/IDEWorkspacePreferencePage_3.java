		workspaceName = new StringFieldEditor(IDEInternalPreferences.WORKSPACE_NAME,
				IDEWorkbenchMessages.IDEWorkspacePreference_workspaceName, compositeWsName);
		gl = ((GridLayout) compositeWsName.getLayout());
		gl.marginLeft = 15;
		gl.marginHeight = 0;

		workspaceName.setPreferenceStore(getIDEPreferenceStore());
		workspaceName.load();
		workspaceName.setPage(this);

		showPerspectiveNameInTitle = new Button(grpWindowTitle, SWT.CHECK);
		showPerspectiveNameInTitle
				.setText(IDEWorkbenchMessages.IDEWorkspacePreference_showPerspectiveNameInWindowTitle);
		showPerspectiveNameInTitle.setSelection(isShowPerspective);

		showLocationPathInTitle = new Button(grpWindowTitle, SWT.CHECK);
