	protected Control createDialogArea(Composite parent) {
		boolean advancedMode = Activator.getDefault().getPreferenceStore()
				.getBoolean(ADVANCED_MODE_PREFERENCE);
		final Composite main = new Composite(parent, SWT.NONE);
		main.setLayout(new GridLayout(1, false));
		GridDataFactory.fillDefaults().grab(true, true).minSize(SWT.DEFAULT, SWT.DEFAULT).applyTo(main);

		if (showBranchInfo) {
			Composite branchArea = new Composite(main, SWT.NONE);
			GridLayoutFactory.swtDefaults().numColumns(2).equalWidth(false)
					.applyTo(branchArea);
			GridDataFactory.fillDefaults().grab(true, false)
					.applyTo(branchArea);

			Label branchLabel = new Label(branchArea, SWT.NONE);
			branchLabel.setText(UIText.SimpleConfigurePushDialog_BranchLabel);
			String branch;
			try {
				branch = repository.getBranch();
			} catch (IOException e2) {
				branch = null;
			}
			if (branch == null || ObjectId.isId(branch))
				branch = UIText.SimpleConfigurePushDialog_DetachedHeadMessage;
			Text branchText = new Text(branchArea, SWT.BORDER | SWT.READ_ONLY);
			GridDataFactory.fillDefaults().grab(true, false)
					.applyTo(branchText);
			branchText.setText(branch);
		}

		addDefaultOriginWarningIfNeeded(main);

		final Composite sameUriDetails = new Composite(main, SWT.NONE);
		sameUriDetails.setLayout(new GridLayout(4, false));
		GridDataFactory.fillDefaults().grab(true, false)
				.applyTo(sameUriDetails);
		Label commonUriLabel = new Label(sameUriDetails, SWT.NONE);
		commonUriLabel.setText(UIText.SimpleConfigurePushDialog_URILabel);
		commonUriText = new Text(sameUriDetails, SWT.BORDER | SWT.READ_ONLY);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(commonUriText);
		changeCommonUri = new Button(sameUriDetails, SWT.PUSH);
		changeCommonUri
				.setText(UIText.SimpleConfigurePushDialog_ChangeUriButton);
		changeCommonUri.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SelectUriWizard wiz;
				if (commonUriText.getText().length() > 0)
					wiz = new SelectUriWizard(false, commonUriText.getText());
				else
					wiz = new SelectUriWizard(false);
				if (new WizardDialog(getShell(), wiz).open() == Window.OK) {
					if (commonUriText.getText().length() > 0)
						try {
							config.removeURI(new URIish(commonUriText.getText()));
						} catch (URISyntaxException ex) {
							Activator.handleError(ex.getMessage(), ex, true);
						}
					config.addURI(wiz.getUri());
					updateControls();
				}
			}
		});

		deleteCommonUri = new Button(sameUriDetails, SWT.PUSH);
		deleteCommonUri
				.setText(UIText.SimpleConfigurePushDialog_DeleteUriButton);
		deleteCommonUri.setEnabled(false);
		deleteCommonUri.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				config.removeURI(config.getURIs().get(0));
				updateControls();
			}
		});

		commonUriText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				deleteCommonUri
						.setEnabled(commonUriText.getText().length() > 0);
			}
		});

		ExpandableComposite pushUriArea = new ExpandableComposite(main,
