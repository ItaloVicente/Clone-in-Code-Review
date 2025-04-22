		Label nameLabel = new Label(main, SWT.NONE);
		nameLabel.setText(UIText.CreateRepositoryPage_RepositoryNameLabel);
		nameText = new Text(main, SWT.BORDER);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER)
				.grab(true, false).applyTo(nameText);

