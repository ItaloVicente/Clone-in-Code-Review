		showLocationNameInTitle = new Button(grpWindowTitle, SWT.RADIO);
		showLocationNameInTitle.setText(IDEWorkbenchMessages.IDEWorkspacePreference_showLocationNameInWindowTitle);
		showLocationNameInTitle.setSelection(isShowName);

		Composite compositeWsName = new Composite(grpWindowTitle, SWT.NONE);
		compositeWsName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		workspaceName = new StringFieldEditor(IDEInternalPreferences.WORKSPACE_NAME,
				IDEWorkbenchMessages.IDEWorkspacePreference_workspaceName, compositeWsName);
		gl = ((GridLayout) compositeWsName.getLayout());
		gl.marginLeft = 15;
		gl.marginHeight = 0;

		workspaceName.setPreferenceStore(getIDEPreferenceStore());
		workspaceName.load();
		workspaceName.setPage(this);

		Stream.of(grpWindowTitle, showLocationPathInTitle, locationLabel, workspacePath, showLocationNameInTitle,
				workspaceName.getLabelControl(compositeWsName), workspaceName.getTextControl(compositeWsName))
				.forEach(c -> c.setEnabled(!showLocationIsSetOnCommandLine));
