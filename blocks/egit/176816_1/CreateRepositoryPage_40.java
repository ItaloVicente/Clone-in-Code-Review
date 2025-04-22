		Label defaultBranchLabel = new Label(main, SWT.NONE);
		defaultBranchLabel
				.setText(UIText.CreateRepositoryPage_DefaultBranchLabel);
		defaultBranchText = new Text(main, SWT.BORDER);
		String defaultBranch;
		try {
			defaultBranch = RepositoryUtil.getDefaultBranchName();
			if (StringUtils.isEmptyOrNull(defaultBranch)) {
				defaultBranch = Constants.MASTER;
			}
		} catch (ConfigInvalidException | IOException e) {
			defaultBranch = Constants.MASTER;
			Activator.handleError(
					UIText.CreateRepositoryPage_ReadDefaultBranchFailed, e,
					true);
		}
		defaultBranchText.setText(defaultBranch);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER)
				.grab(true, false).applyTo(defaultBranchText);

		defaultBranchText.addModifyListener(event -> checkPage());

