			buildNowButton.addSelectionListener(updateEnablement);

			globalBuildButton = new Button(parent, SWT.RADIO);
			globalBuildButton.setText(IDEWorkbenchMessages.CleanDialog_globalBuildButton);
			String buildAll = settings.get(BUILD_ALL);
			globalBuildButton.setSelection(buildAll == null || Boolean.parseBoolean(buildAll));
			GridData data = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
			data.horizontalIndent = 10;
			globalBuildButton.setLayoutData(data);
			globalBuildButton.setEnabled(buildNowButton.getSelection());

			projectBuildButton = new Button(parent, SWT.RADIO);
			projectBuildButton.setText(IDEWorkbenchMessages.CleanDialog_buildSelectedProjectsButton);
			projectBuildButton.setSelection(!globalBuildButton.getSelection());
			data = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
			data.horizontalIndent = 10;
			projectBuildButton.setLayoutData(data);
			projectBuildButton.setEnabled(buildNowButton.getSelection());
