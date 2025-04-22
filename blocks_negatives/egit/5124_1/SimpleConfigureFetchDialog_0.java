		repositoryGroup.setLayout(new GridLayout(2, false));
		GridDataFactory.fillDefaults().grab(true, false).applyTo(
				repositoryGroup);
		Label repositoryLabel = new Label(repositoryGroup, SWT.NONE);
		repositoryLabel
				.setText(UIText.SimpleConfigureFetchDialog_RepositoryLabel);
		Text repositoryText = new Text(repositoryGroup, SWT.BORDER
				| SWT.READ_ONLY);
