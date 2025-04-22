		Composite commitMessageComposite = new Composite(horizontalSashForm, SWT.NONE);
		commitMessageComposite.setLayout(new GridLayout(2, false));

		Label commitMessageLabel = new Label(commitMessageComposite, SWT.NONE);
		commitMessageLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		commitMessageLabel.setText(UIText.StagingView_CommitMessage);

		commitMessageText = new SpellcheckableMessageArea(commitMessageComposite, EMPTY_STRING);
		commitMessageText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		Composite composite = new Composite(commitMessageComposite, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		composite.setLayout(new GridLayout(2, false));

		new Label(composite, SWT.NONE).setText(UIText.StagingView_Committer);

		committerText = new Text(composite, SWT.BORDER);
		committerText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		new Label(composite, SWT.NONE).setText(UIText.StagingView_Author);

		authorText = new Text(composite, SWT.BORDER);
		authorText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		amendPreviousCommitButton = new Button(commitMessageComposite, SWT.CHECK);
		amendPreviousCommitButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		amendPreviousCommitButton.setText(UIText.StagingView_Ammend_Previous_Commit);

		signedOffByButton = new Button(commitMessageComposite, SWT.CHECK);
		signedOffByButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		signedOffByButton.setText(UIText.StagingView_Add_Signed_Off_By);

		addChangeIdButton = new Button(commitMessageComposite, SWT.CHECK);
		GridData addChangeIdButtonGridData = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		addChangeIdButtonGridData.minimumHeight = 1;
		addChangeIdButton.setLayoutData(addChangeIdButtonGridData);
		addChangeIdButton.setText(UIText.StagingView_Add_Change_ID);

		final ICommitMessageComponentNotifications listener = new ICommitMessageComponentNotifications() {

			public void updateSignedOffToggleSelection(boolean selection) {
				signedOffByButton.setSelection(selection);
			}

			public void updateChangeIdToggleSelection(boolean selection) {
				addChangeIdButton.setSelection(selection);
			}
		};
		commitMessageComponent = new CommitMessageComponent(listener);
		commitMessageComponent.attachControls(commitMessageText, authorText, committerText);
		addChangeIdButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				commitMessageComponent.setChangeIdButtonSelection(addChangeIdButton.getSelection());
			}
		});
		signedOffByButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				commitMessageComponent.setSignedOffButtonSelection(signedOffByButton.getSelection());
			}
		});
		amendPreviousCommitButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				commitMessageComponent.setAmendingButtonSelection(amendPreviousCommitButton.getSelection());
			}
		});


		commitButton = new Button(commitMessageComposite, SWT.NONE);
		commitButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		commitButton.setText(UIText.StagingView_Commit);
		commitButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				commit();
			}

		});

		Composite stagedComposite = new Composite(veriticalSashForm, SWT.NONE);
		stagedComposite.setLayout(new GridLayout(1, false));

		new Label(stagedComposite, SWT.NONE).setText(UIText.StagingView_StagedChanges);

		Composite stagedTableComposite = new Composite(stagedComposite, SWT.NONE);
		stagedTableComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		stagedTableComposite.setLayout(new TableColumnLayout());

		stagedTableViewer = new TableViewer(stagedTableComposite, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
