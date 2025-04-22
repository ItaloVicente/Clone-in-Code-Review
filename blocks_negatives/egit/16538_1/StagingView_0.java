		rebaseLabel = toolkit.createLabel(buttonsContainer,
				UIText.StagingView_RebaseLabel);
		rebaseLabel.setForeground(toolkit.getColors().getColor(
				IFormColors.TB_TOGGLE));
		GridDataFactory.fillDefaults().align(SWT.END, SWT.CENTER)
				.applyTo(rebaseLabel);

		GridDataFactory width2 = GridDataFactory.fillDefaults()
				.span(2, 1).align(SWT.FILL, SWT.CENTER);
		this.rebaseAbortButton = toolkit.createButton(buttonsContainer,
				UIText.StagingView_RebaseAbort, SWT.PUSH);
		rebaseAbortButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				rebaseAbort();
			}
		});
		rebaseAbortButton.setImage(getImage(UIIcons.REBASE_ABORT));
		width2.applyTo(rebaseAbortButton);

		this.rebaseSkipButton = toolkit.createButton(buttonsContainer,
				UIText.StagingView_RebaseSkip, SWT.PUSH);
		rebaseSkipButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				rebaseSkip();
			}
		});
		rebaseSkipButton.setImage(getImage(UIIcons.REBASE_SKIP));
		width2.applyTo(rebaseSkipButton);

		this.rebaseContinueButton = toolkit.createButton(buttonsContainer,
				UIText.StagingView_RebaseContinue, SWT.PUSH);
		rebaseContinueButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				rebaseContinue();
			}
		});
		rebaseContinueButton.setImage(getImage(UIIcons.REBASE_CONTINUE));
		width2.applyTo(rebaseContinueButton);

		updateRebaseButtonVisibility(false);
