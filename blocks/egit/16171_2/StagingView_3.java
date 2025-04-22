		buttonGridDataFactory.applyTo(commitButton);

		rebaseLabel = toolkit.createLabel(buttonsContainer,
				UIText.StagingView_RebaseLabel);
		rebaseLabel.setForeground(toolkit.getColors().getColor(
				IFormColors.TB_TOGGLE));
		GridDataFactory.fillDefaults().align(SWT.END, SWT.CENTER)
				.applyTo(rebaseLabel);

		this.rebaseAbortButton = toolkit.createButton(buttonsContainer,
				UIText.StagingView_RebaseAbort, SWT.PUSH);
		rebaseAbortButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				rebaseAbort();
			}
		});
		rebaseAbortButton.setImage(getImage(UIIcons.REBASE_ABORT));
		buttonGridDataFactory.applyTo(rebaseAbortButton);

		this.rebaseSkipButton = toolkit.createButton(buttonsContainer,
				UIText.StagingView_RebaseSkip, SWT.PUSH);
		rebaseSkipButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				rebaseSkip();
			}
		});
		rebaseSkipButton.setImage(getImage(UIIcons.REBASE_SKIP));
		buttonGridDataFactory.applyTo(rebaseSkipButton);

		this.rebaseContinueButton = toolkit.createButton(buttonsContainer,
				UIText.StagingView_RebaseContinue, SWT.PUSH);
		rebaseContinueButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				rebaseContinue();
			}
		});
		rebaseContinueButton.setImage(getImage(UIIcons.REBASE_CONTINUE));
		buttonGridDataFactory.applyTo(rebaseContinueButton);

		updateRebaseButtonVisibility(false);
