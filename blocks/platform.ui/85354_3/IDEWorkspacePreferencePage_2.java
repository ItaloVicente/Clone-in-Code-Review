		GridLayout gl = ((GridLayout) groupComposite.getLayout());
		gl.horizontalSpacing = 0;

		Group grpWindowTitle = new Group(groupComposite, SWT.NONE);
		grpWindowTitle.setText(IDEWorkbenchMessages.IDEWorkspacePreference_windowTitleGroupText); // $NON-NLS-1$
		grpWindowTitle.setLayout(new GridLayout(1, false));
		grpWindowTitle.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		boolean isShowLocation = getIDEPreferenceStore().getBoolean(IDEInternalPreferences.SHOW_LOCATION);
		boolean isShowName = getIDEPreferenceStore().getBoolean(IDEInternalPreferences.SHOW_LOCATION_NAME);
		if (isShowLocation == isShowName) {
			isShowName = !isShowLocation;
		}
