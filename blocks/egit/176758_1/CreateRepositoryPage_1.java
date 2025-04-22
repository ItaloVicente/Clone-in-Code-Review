		Label defaultBranchLabel = new Label(main, SWT.NONE);
		defaultBranchLabel
				.setText(UIText.CreateRepositoryPage_defaultBranchLabel);
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
					UIText.CreateRepositoryPage_readDefaultBranchFailed,
					e,
					true);
		}
		defaultBranchText.setText(defaultBranch);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER)
				.grab(true, false).applyTo(defaultBranchText);

		defaultBranchText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				checkPage();
			}
		});

