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
			placeholder = new Label(main, SWT.NONE);
			GridDataFactory.fillDefaults().span(2, 1).applyTo(placeholder);
			cherryPickFetchHead.setSelection(true);
			placeholder = new Label(main, SWT.NONE);
			GridDataFactory.fillDefaults().span(2, 1).applyTo(placeholder);
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
