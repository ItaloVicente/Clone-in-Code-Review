			clipText = (String) clipboard
					.getContents(TextTransfer.getInstance());
		} finally {
			clipboard.dispose();
		}
		String defaultUri = null;
		String defaultCommand = null;
		String defaultChange = null;
		Change candidateChange = null;
		if (clipText != null) {
			Matcher matcher = GERRIT_FETCH_PATTERN.matcher(clipText);
			if (matcher.matches()) {
				defaultUri = matcher.group(1);
				defaultChange = matcher.group(2);
				defaultCommand = matcher.group(3);
			} else {
				candidateChange = determineChangeFromString(clipText.trim());
			}
		}
		SelectionAdapter validatePage = new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				checkPage();
			}
		};
		Composite main = new Composite(parent, SWT.NONE);
		main.setLayout(new GridLayout(2, false));
		GridDataFactory.fillDefaults().grab(true, true).applyTo(main);
		new Label(main, SWT.NONE)
				.setText(UIText.FetchGerritChangePage_UriLabel);
		uriCombo = new Combo(main, SWT.DROP_DOWN);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(uriCombo);
		uriCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String uriText = uriCombo.getText();
				ChangeList list = changeRefs.get(uriText);
				if (list != null) {
					list.cancel(ChangeList.CancelMode.INTERRUPT);
				}
				list = new ChangeList(repository, uriText);
				changeRefs.put(uriText, list);
				preFetch(list);
			}
		});
		new Label(main, SWT.NONE)
				.setText(UIText.FetchGerritChangePage_ChangeLabel);
		refText = new Text(main, SWT.SINGLE | SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(refText);
		contentProposer = addRefContentProposalToText(refText);
		refText.addVerifyListener(
				event -> event.text = Utils.toSingleLine(event.text).trim());

		final Group checkoutGroup = new Group(main, SWT.SHADOW_ETCHED_IN);
		checkoutGroup.setLayout(new GridLayout(3, false));
		GridDataFactory.fillDefaults().span(3, 1).grab(true, false)
				.applyTo(checkoutGroup);
		checkoutGroup.setText(UIText.FetchGerritChangePage_AfterFetchGroup);

		createBranch = new Button(checkoutGroup, SWT.RADIO);
		GridDataFactory.fillDefaults().span(1, 1).applyTo(createBranch);
		createBranch.setText(UIText.FetchGerritChangePage_LocalBranchRadio);
		createBranch.addSelectionListener(validatePage);

		branchCheckoutButton = new Button(checkoutGroup, SWT.CHECK);
		GridDataFactory.fillDefaults().span(2, 1).align(SWT.END, SWT.CENTER)
				.applyTo(branchCheckoutButton);
		branchCheckoutButton.setFont(JFaceResources.getDialogFont());
		branchCheckoutButton
				.setText(UIText.FetchGerritChangePage_LocalBranchCheckout);
		branchCheckoutButton.setSelection(true);

		branchTextlabel = new Label(checkoutGroup, SWT.NONE);
		GridDataFactory.defaultsFor(branchTextlabel).exclude(false)
				.applyTo(branchTextlabel);
		branchTextlabel.setText(UIText.FetchGerritChangePage_BranchNameText);
		branchText = new Text(checkoutGroup, SWT.SINGLE | SWT.BORDER);
		GridDataFactory.fillDefaults().grab(true, false)
				.align(SWT.FILL, SWT.CENTER).applyTo(branchText);
		branchText.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				branchTextEdited = true;
			}
		});
		branchText.addVerifyListener(event -> {
			if (event.text.isEmpty()) {
				branchTextEdited = false;
			}
		});
		branchText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				checkPage();
			}
		});
		BranchNameNormalizer normalizer = new BranchNameNormalizer(branchText);
		normalizer.setVisible(false);
		branchEditButton = new Button(checkoutGroup, SWT.PUSH);
		branchEditButton.setFont(JFaceResources.getDialogFont());
		branchEditButton.setText(UIText.FetchGerritChangePage_BranchEditButton);
		branchEditButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent selectionEvent) {
				String txt = branchText.getText();
				String refToMark = txt.isEmpty() ? null : Constants.R_HEADS + txt;
				AbstractBranchSelectionDialog dlg = new BranchEditDialog(
						checkoutGroup.getShell(), repository, refToMark);
				if (dlg.open() == Window.OK) {
					branchText.setText(Repository.shortenRefName(dlg
							.getRefName()));
					branchTextEdited = true;
				} else {
					branchText.setText(branchText.getText());
				}
			}
		});
		GridDataFactory.defaultsFor(branchEditButton).exclude(false)
				.applyTo(branchEditButton);

		createTag = new Button(checkoutGroup, SWT.RADIO);
		GridDataFactory.fillDefaults().span(3, 1).applyTo(createTag);
		createTag.setText(UIText.FetchGerritChangePage_TagRadio);
		createTag.addSelectionListener(validatePage);

		tagTextlabel = new Label(checkoutGroup, SWT.NONE);
		GridDataFactory.defaultsFor(tagTextlabel).exclude(true)
				.applyTo(tagTextlabel);
		tagTextlabel.setText(UIText.FetchGerritChangePage_TagNameText);
		tagText = new Text(checkoutGroup, SWT.SINGLE | SWT.BORDER);
		GridDataFactory.fillDefaults().exclude(true).grab(true, false)
				.applyTo(tagText);
		tagText.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				tagTextEdited = true;
			}
		});
		tagText.addVerifyListener(event -> {
			if (event.text.isEmpty()) {
				tagTextEdited = false;
			}
		});
		tagText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				checkPage();
			}
		});
		BranchNameNormalizer tagNormalizer = new BranchNameNormalizer(tagText,
				UIText.BranchNameNormalizer_TooltipForTag);
		tagNormalizer.setVisible(false);

		checkoutFetchHead = new Button(checkoutGroup, SWT.RADIO);
		GridDataFactory.fillDefaults().span(3, 1).applyTo(checkoutFetchHead);
		checkoutFetchHead.setText(UIText.FetchGerritChangePage_CheckoutRadio);
		checkoutFetchHead.addSelectionListener(validatePage);

		try {
			String headName = repository.getBranch();
			if (headName != null) {
				cherryPickFetchHead = new Button(checkoutGroup, SWT.RADIO);
				GridDataFactory.fillDefaults().span(3, 1)
						.applyTo(cherryPickFetchHead);
				cherryPickFetchHead.setText(MessageFormat.format(
						UIText.FetchGerritChangePage_CherryPickRadio,
						headName));
				cherryPickFetchHead.addSelectionListener(validatePage);
			}
		} catch (IOException e) {
			Activator.logError(e.getLocalizedMessage(), e);
		}

		updateFetchHead = new Button(checkoutGroup, SWT.RADIO);
		GridDataFactory.fillDefaults().span(3, 1).applyTo(updateFetchHead);
		updateFetchHead.setText(UIText.FetchGerritChangePage_UpdateRadio);
		updateFetchHead.addSelectionListener(validatePage);

			checkoutFetchHead.setSelection(true);
		} else {
			createBranch.setSelection(true);
		}

		warningAdditionalRefNotActive = new Composite(main, SWT.NONE);
		GridDataFactory.fillDefaults().span(2, 1).grab(true, false)
				.exclude(true).applyTo(warningAdditionalRefNotActive);
		warningAdditionalRefNotActive.setLayout(new GridLayout(2, false));
		warningAdditionalRefNotActive.setVisible(false);

		activateAdditionalRefs = new Button(warningAdditionalRefNotActive,
				SWT.CHECK);
		activateAdditionalRefs
				.setText(UIText.FetchGerritChangePage_ActivateAdditionalRefsButton);
		activateAdditionalRefs
				.setToolTipText(
						UIText.FetchGerritChangePage_ActivateAdditionalRefsTooltip);

		ActionUtils.setGlobalActions(refText, ActionUtils.createGlobalAction(
				ActionFactory.PASTE, () -> {
					if (doPaste(refText) && contentProposer != null) {
						refText.getDisplay().asyncExec(() -> {
							if (!refText.isDisposed()) {
								contentProposer.openProposalPopup();
							}
						});
					}
				}));
		refText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				refTextEdited = true;
				Change change = determineChangeFromString(refText.getText());
				if (change != null) {
					Object ps = change.getPatchSetNumber();
					if (ps == null) {
						ps = SIMPLE_TIMESTAMP.format(new Date());
					}
					suggestion = NLS.bind(
							UIText.FetchGerritChangePage_SuggestedRefNamePattern,
							change.getChangeNumber(),
							ps);
				}
				if (!branchTextEdited) {
					branchText.setText(suggestion);
				}
				if (!tagTextEdited) {
					tagText.setText(suggestion);
				}
				checkPage();
			}
		});
		if (defaultChange != null) {
			refText.setText(defaultChange);
		} else if (candidateChange != null) {
			String ref = candidateChange.getRefName();
			if (ref != null) {
				refText.setText(ref);
			} else {
				refText.setText(candidateChange.getChangeNumber().toString());
			}
		}
		refTextEdited = false;

		SortedSet<String> uris = new TreeSet<>();
		try {
			for (RemoteConfig rc : RemoteConfig.getAllRemoteConfigs(repository
					.getConfig())) {
