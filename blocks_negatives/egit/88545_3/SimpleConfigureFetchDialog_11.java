	protected Control createDialogArea(Composite parent) {
		boolean advancedMode = Activator.getDefault().getPreferenceStore()
				.getBoolean(ADVANCED_MODE_PREFERENCE);
		final Composite main = new Composite(parent, SWT.NONE);
		main.setLayout(new GridLayout(1, false));
		GridDataFactory.fillDefaults().grab(true, true).applyTo(main);

		if (showBranchInfo) {
			Composite branchArea = new Composite(main, SWT.NONE);
			GridLayoutFactory.swtDefaults().numColumns(2).equalWidth(false)
					.applyTo(branchArea);
			GridDataFactory.fillDefaults().grab(true, false)
					.applyTo(branchArea);

			Label branchLabel = new Label(branchArea, SWT.NONE);
			branchLabel.setText(UIText.SimpleConfigureFetchDialog_BranchLabel);
			String branch;
			try {
				branch = repository.getBranch();
			} catch (IOException e2) {
				branch = null;
			}
			if (branch == null || ObjectId.isId(branch))
				branch = UIText.SimpleConfigureFetchDialog_DetachedHeadMessage;
			Text branchText = new Text(branchArea, SWT.BORDER | SWT.READ_ONLY);
			GridDataFactory.fillDefaults().grab(true, false)
					.applyTo(branchText);
			branchText.setText(branch);
		}

		addDefaultOriginWarningIfNeeded(main);

		final Composite sameUriDetails = new Composite(main, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(4).equalWidth(false)
				.applyTo(sameUriDetails);
		GridDataFactory.fillDefaults().grab(true, false)
				.applyTo(sameUriDetails);
		Label commonUriLabel = new Label(sameUriDetails, SWT.NONE);
		commonUriLabel.setText(UIText.SimpleConfigureFetchDialog_UriLabel);
		commonUriText = new Text(sameUriDetails, SWT.BORDER | SWT.READ_ONLY);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(commonUriText);
		changeCommonUri = new Button(sameUriDetails, SWT.PUSH);
		changeCommonUri
				.setText(UIText.SimpleConfigureFetchDialog_ChangeUriButton);
		changeCommonUri.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SelectUriWizard wiz;
				if (commonUriText.getText().length() > 0)
					wiz = new SelectUriWizard(true, commonUriText.getText());
				else
					wiz = new SelectUriWizard(true);
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

		final Button deleteCommonUri = new Button(sameUriDetails, SWT.PUSH);
		deleteCommonUri
				.setText(UIText.SimpleConfigureFetchDialog_DeleteUriButton);
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

		final Group refSpecGroup = new Group(main, SWT.SHADOW_ETCHED_IN);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(refSpecGroup);
		refSpecGroup.setText(UIText.SimpleConfigureFetchDialog_RefMappingGroup);
		refSpecGroup.setLayout(new GridLayout(2, false));

		specViewer = new TableViewer(refSpecGroup, SWT.BORDER | SWT.MULTI);
		specViewer.setContentProvider(ArrayContentProvider.getInstance());
		GridDataFactory.fillDefaults().hint(SWT.DEFAULT, 150)
				.minSize(SWT.DEFAULT, 30).grab(true, true)
				.applyTo(specViewer.getTable());

		specViewer.getTable().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.stateMask == SWT.MOD1 && e.keyCode == 'v')
					doPaste();
			}
		});

		Composite buttonArea = new Composite(refSpecGroup, SWT.NONE);
		GridLayoutFactory.fillDefaults().applyTo(buttonArea);
		GridDataFactory.fillDefaults().grab(false, true).applyTo(buttonArea);

		addRefSpec = new Button(buttonArea, SWT.PUSH);
		addRefSpec.setText(UIText.SimpleConfigureFetchDialog_AddRefSpecButton);
		GridDataFactory.fillDefaults().applyTo(addRefSpec);
		addRefSpec.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SimpleFetchRefSpecWizard wiz = new SimpleFetchRefSpecWizard(
						repository, config);
				WizardDialog dlg = new WizardDialog(getShell(), wiz);
				if (dlg.open() == Window.OK)
					config.addFetchRefSpec(wiz.getSpec());
				updateControls();
			}
		});

		changeRefSpec = new Button(buttonArea, SWT.PUSH);
		changeRefSpec
				.setText(UIText.SimpleConfigureFetchDialog_ChangeRefSpecButton);
		changeRefSpec.setEnabled(false);
		GridDataFactory.fillDefaults().exclude(!advancedMode)
				.applyTo(changeRefSpec);
		changeRefSpec.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				RefSpec oldSpec = (RefSpec) ((IStructuredSelection) specViewer
						.getSelection()).getFirstElement();
				RefSpecDialog dlg = new RefSpecDialog(getShell(), repository,
						config, oldSpec, false);
				if (dlg.open() == Window.OK) {
					config.removeFetchRefSpec(oldSpec);
					config.addFetchRefSpec(dlg.getSpec());
				}
				updateControls();
			}
		});
		final Button deleteRefSpec = new Button(buttonArea, SWT.PUSH);
		deleteRefSpec
				.setText(UIText.SimpleConfigureFetchDialog_DeleteRefSpecButton);
		GridDataFactory.fillDefaults().applyTo(deleteRefSpec);
		deleteRefSpec.setEnabled(false);
		deleteRefSpec.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (Object spec : ((IStructuredSelection) specViewer
						.getSelection()).toArray())
					config.removeFetchRefSpec((RefSpec) spec);
				updateControls();
			}
		});

		final Button copySpec = new Button(buttonArea, SWT.PUSH);
		copySpec.setText(UIText.SimpleConfigureFetchDialog_CopyRefSpecButton);
		GridDataFactory.fillDefaults().applyTo(copySpec);
		copySpec.setEnabled(false);
		copySpec.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String toCopy = ((IStructuredSelection) specViewer
						.getSelection()).getFirstElement().toString();
				Clipboard clipboard = new Clipboard(getShell().getDisplay());
				try {
					clipboard.setContents(new String[] { toCopy },
							new TextTransfer[] { TextTransfer.getInstance() });
				} finally {
					clipboard.dispose();
				}
			}
		});

		final Button pasteSpec = new Button(buttonArea, SWT.PUSH);
		pasteSpec.setText(UIText.SimpleConfigureFetchDialog_PateRefSpecButton);
		GridDataFactory.fillDefaults().applyTo(pasteSpec);
		pasteSpec.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				doPaste();
			}
		});

		addRefSpecAdvanced = new Button(buttonArea, SWT.PUSH);
		GridDataFactory.fillDefaults().applyTo(addRefSpecAdvanced);

		addRefSpecAdvanced
				.setText(UIText.SimpleConfigureFetchDialog_EditAdvancedButton);
		addRefSpecAdvanced.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (new WizardDialog(getShell(), new RefSpecWizard(repository,
						config, false)).open() == Window.OK)
					updateControls();
			}
		});

		specViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection sel = (IStructuredSelection) specViewer
						.getSelection();
				copySpec.setEnabled(sel.size() == 1);
				changeRefSpec.setEnabled(sel.size() == 1);
				deleteRefSpec.setEnabled(!sel.isEmpty());
			}
		});

		applyDialogFont(main);
		return main;
