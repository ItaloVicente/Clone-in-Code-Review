		Composite rebaseAndCommitComposite = toolkit.createComposite(horizontalSashForm);
		rebaseAndCommitComposite.setLayout(GridLayoutFactory.fillDefaults().create());

		rebaseSection = toolkit.createSection(rebaseAndCommitComposite,
				ExpandableComposite.TITLE_BAR);
		rebaseSection.setText("Rebase"); //$NON-NLS-1$

		Composite rebaseComposite = toolkit.createComposite(rebaseSection);
		toolkit.paintBordersFor(rebaseComposite);
		rebaseSection.setClient(rebaseComposite);

		rebaseSection.setLayoutData(GridDataFactory.fillDefaults().create());
		rebaseComposite.setLayout(GridLayoutFactory.fillDefaults()
				.numColumns(3).create());

		this.rebaseAbortButton = toolkit.createButton(rebaseComposite,
				UIText.StagingView_RebaseAbort, SWT.PUSH);
		rebaseAbortButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				rebaseAbort();
			}
		});
		rebaseAbortButton.setImage(getImage(UIIcons.REBASE_ABORT));

		this.rebaseSkipButton = toolkit.createButton(rebaseComposite,
				UIText.StagingView_RebaseSkip, SWT.PUSH);
		rebaseSkipButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				rebaseSkip();
			}
		});
		rebaseSkipButton.setImage(getImage(UIIcons.REBASE_SKIP));

		this.rebaseContinueButton = toolkit.createButton(rebaseComposite,
				UIText.StagingView_RebaseContinue, SWT.PUSH);
		rebaseContinueButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				rebaseContinue();
			}
		});
		rebaseContinueButton.setImage(getImage(UIIcons.REBASE_CONTINUE));

		updateRebaseButtonVisibility(false);

		commitMessageSection = toolkit.createSection(rebaseAndCommitComposite,
				ExpandableComposite.TITLE_BAR);
