	private void createWorkspaceLocationGroup(Composite composite) {
		Composite groupComposite = new Composite(composite, SWT.LEFT);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(groupComposite);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(groupComposite);

		Label locationLabel = new Label(groupComposite, SWT.NONE);
		locationLabel.setText(IDEWorkbenchMessages.IDEWorkspacePreference_workspaceLocation);
		GridDataFactory.fillDefaults().applyTo(locationLabel);
		Text workspacePath = new Text(groupComposite, SWT.READ_ONLY);
		workspacePath.setBackground(workspacePath.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
		workspacePath.setText(getTrueWorkspaceLocation());
		GridDataFactory.fillDefaults().grab(true, false).applyTo(workspacePath);

		String workspaceLocationFromCommandLine = getWorkspaceLocationFromCommandLine();
		if (workspaceLocationFromCommandLine != null) {
			Button showWorkspace = new Button(groupComposite, SWT.CHECK);
			if (getTrueWorkspaceLocation().equals(workspaceLocationFromCommandLine)) {
				showWorkspace.setText(IDEWorkbenchMessages.IDEWorkspacePreference_showLocationInWindowTitle);
			} else {
				showWorkspace.setText(NLS.bind(
						IDEWorkbenchMessages.IDEWorkspacePreference_showLocationInWindowTitle_withValue,
						workspaceLocationFromCommandLine));
			}
			showWorkspace.setSelection(true);
			showWorkspace.setEnabled(false);
			showWorkspace.setToolTipText(workspaceLocationFromCommandLine);
			GridDataFactory.fillDefaults().grab(true, false).span(2, 1).applyTo(showWorkspace);
		} else {
			Composite showComposite = new Composite(composite, SWT.LEFT);
			GridDataFactory.fillDefaults().grab(true, false).applyTo(showComposite);
			GridLayoutFactory.fillDefaults().applyTo(showComposite);
			showLocationInWindowTitle = new BooleanFieldEditor(IDEInternalPreferences.SHOW_LOCATION,
					IDEWorkbenchMessages.IDEWorkspacePreference_showLocationInWindowTitle, showComposite);
			showLocationInWindowTitle.setPage(this);
			showLocationInWindowTitle.setPreferenceStore(getIDEPreferenceStore());
			showLocationInWindowTitle.load();
		}
	}

