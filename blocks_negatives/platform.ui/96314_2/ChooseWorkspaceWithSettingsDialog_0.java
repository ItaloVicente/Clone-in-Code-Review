		GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
		form.setLayoutData(layoutData);
		final ExpandableComposite expandable = toolkit
				.createExpandableComposite(form.getBody(),
						ExpandableComposite.TWISTIE);
		expandable
				.setText(IDEWorkbenchMessages.ChooseWorkspaceWithSettingsDialog_SettingsGroupName);
