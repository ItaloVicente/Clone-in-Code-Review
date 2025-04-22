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
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID,
				UIText.SimpleConfigureFetchDialog_SaveAndFetchButton, true);
		createButton(parent, SAVE_ONLY,
				UIText.SimpleConfigureFetchDialog_SaveButton, false);
		createButton(parent, DRY_RUN,
				UIText.SimpleConfigureFetchDialog_DryRunButton, false);

		createButton(parent, REVERT,
				UIText.SimpleConfigureFetchDialog_RevertButton, false);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(UIText.SimpleConfigureFetchDialog_WindowTitle);
	}

	@Override
	public void create() {
		super.create();
		setTitle(NLS.bind(UIText.SimpleConfigureFetchDialog_DialogTitle,
				config.getName()));
		setMessage(UIText.SimpleConfigureFetchDialog_DialogMessage);

		updateControls();
	}

	private void updateControls() {
		setErrorMessage(null);
		boolean anyUri = false;
		if (!config.getURIs().isEmpty()) {
			commonUriText.setText(config.getURIs().get(0).toPrivateString());
			anyUri = true;
		} else
			commonUriText.setText(""); //$NON-NLS-1$

		specViewer.setInput(config.getFetchRefSpecs());
		specViewer.getTable().setEnabled(true);

		addRefSpec.setEnabled(anyUri);
		addRefSpecAdvanced.setEnabled(anyUri);

		if (config.getURIs().isEmpty())
			setErrorMessage(UIText.SimpleConfigureFetchDialog_MissingUriMessage);
		else if (config.getFetchRefSpecs().isEmpty())
			setErrorMessage(UIText.SimpleConfigureFetchDialog_MissingMappingMessage);

		boolean anySpec = !config.getFetchRefSpecs().isEmpty();
		getButton(OK).setEnabled(anyUri && anySpec);
		getButton(SAVE_ONLY).setEnabled(anyUri && anySpec);
	}

	@Override
	protected void buttonPressed(int buttonId) {
		if (buttonId == DRY_RUN) {
			try {
				new ProgressMonitorDialog(getShell()).run(true, true,
						new IRunnableWithProgress() {
							@Override
							public void run(IProgressMonitor monitor)
									throws InvocationTargetException,
									InterruptedException {
								int timeout = Activator
										.getDefault()
										.getPreferenceStore()
										.getInt(UIPreferences.REMOTE_CONNECTION_TIMEOUT);
								final FetchOperationUI op = new FetchOperationUI(
										repository, config, timeout, true);
								try {
									final FetchResult result = op
											.execute(monitor);
									getShell().getDisplay().asyncExec(
											new Runnable() {

												@Override
												public void run() {
													FetchResultDialog dlg;
													dlg = new FetchResultDialog(
															getShell(),
															repository,
															result,
															op.getSourceString());
													dlg.showConfigureButton(false);
													dlg.open();
												}
											});
								} catch (CoreException e) {
									Activator.handleError(e.getMessage(), e,
											true);
								}

							}
						});
			} catch (InvocationTargetException e1) {
				Activator.handleError(e1.getMessage(), e1, true);
			} catch (InterruptedException e1) {
			}
			return;
		}
		if (buttonId == REVERT) {
			try {
				config = new RemoteConfig(repository.getConfig(), config
						.getName());
				updateControls();
			} catch (URISyntaxException e) {
				Activator.handleError(e.getMessage(), e, true);
			}
			return;
		}
		if (buttonId == OK || buttonId == SAVE_ONLY) {
			config.update(repository.getConfig());
			try {
				repository.getConfig().save();
			} catch (IOException e) {
				Activator.handleError(e.getMessage(), e, true);
			}
			GerritDialogSettings.updateRemoteConfig(repository, config);
			if (buttonId == OK)
				try {
					new ProgressMonitorDialog(getShell()).run(true, true,
							new IRunnableWithProgress() {
								@Override
								public void run(IProgressMonitor monitor)
										throws InvocationTargetException,
										InterruptedException {
									int timeout = Activator
											.getDefault()
											.getPreferenceStore()
											.getInt(
													UIPreferences.REMOTE_CONNECTION_TIMEOUT);
									FetchOperationUI op = new FetchOperationUI(
											repository, config, timeout, false);
									op.start();
								}
							});
				} catch (InvocationTargetException e) {
					Activator.handleError(e.getMessage(), e, true);
				} catch (InterruptedException e) {
					Activator.handleError(e.getMessage(), e, true);
				}
			okPressed();
			return;
		}
		super.buttonPressed(buttonId);
	}

	private void doPaste() {
		Clipboard clipboard = new Clipboard(getShell().getDisplay());
		try {
			String content = (String) clipboard.getContents(TextTransfer
					.getInstance());
			if (content == null)
				MessageDialog
						.openConfirm(
								getShell(),
								UIText.SimpleConfigureFetchDialog_NothingToPasteMessage,
								UIText.SimpleConfigureFetchDialog_EmptyClipboardMessage);
			try {
				RefSpec spec = new RefSpec(content);
				Ref source;
				try {
					source = repository.findRef(spec.getDestination());
				} catch (IOException e1) {
					source = null;
				}
				if (source != null
						|| MessageDialog
								.openQuestion(
										getShell(),
										UIText.SimpleConfigureFetchDialog_InvalidRefDialogTitle,
										NLS
												.bind(
														UIText.SimpleConfigureFetchDialog_InvalidRefDialogMessage,
														spec.toString())))
					config.addFetchRefSpec(spec);

				updateControls();
			} catch (IllegalArgumentException ex) {
				MessageDialog
						.openError(
								getShell(),
								UIText.SimpleConfigureFetchDialog_NotRefSpecDialogTitle,
								UIText.SimpleConfigureFetchDialog_NotRefSpecDialogMessage);
			}
		} finally {
			clipboard.dispose();
		}
	}

	private void addDefaultOriginWarningIfNeeded(Composite parent) {
		if (!showBranchInfo)
			return;
		List<String> otherBranches = new ArrayList<>();
		String currentBranch;
		try {
			currentBranch = repository.getBranch();
		} catch (IOException e) {
			return;
		}
		String currentRemote = config.getName();
		Config repositoryConfig = repository.getConfig();
		Set<String> branches = repositoryConfig
				.getSubsections(ConfigConstants.CONFIG_BRANCH_SECTION);
		for (String branch : branches) {
			if (branch.equals(currentBranch))
				continue;
			String remote = repositoryConfig.getString(
					ConfigConstants.CONFIG_BRANCH_SECTION, branch,
					ConfigConstants.CONFIG_KEY_REMOTE);
			if ((remote == null && currentRemote
					.equals(Constants.DEFAULT_REMOTE_NAME))
					|| (remote != null && remote.equals(currentRemote)))
				otherBranches.add(branch);
		}
		if (otherBranches.isEmpty())
			return;

		Composite warningAboutOrigin = new Composite(parent, SWT.NONE);
		warningAboutOrigin.setLayout(new GridLayout(2, false));
		Label warningLabel = new Label(warningAboutOrigin, SWT.NONE);
		warningLabel.setImage(PlatformUI.getWorkbench().getSharedImages()
				.getImage(ISharedImages.IMG_OBJS_WARN_TSK));
		Text warningText = new Text(warningAboutOrigin, SWT.READ_ONLY);
		warningText.setText(NLS.bind(
				UIText.SimpleConfigureFetchDialog_ReusedRemoteWarning, config
						.getName(), Integer.valueOf(otherBranches.size())));
		warningText.setToolTipText(otherBranches.toString());
		GridDataFactory.fillDefaults().grab(true, false).applyTo(warningLabel);
	}
}
