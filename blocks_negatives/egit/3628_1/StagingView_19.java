		Composite commitMessageComposite = new Composite(horizontalSashForm, SWT.NONE);
		commitMessageComposite.setLayout(new GridLayout(2, false));

		Label commitMessageLabel = new Label(commitMessageComposite, SWT.NONE);
		commitMessageLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		commitMessageLabel.setText(UIText.StagingView_CommitMessage);

		commitMessageText = new SpellcheckableMessageArea(commitMessageComposite, ""); //$NON-NLS-1$
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

		Button amendPreviousCommitButton = new Button(commitMessageComposite, SWT.CHECK);
		amendPreviousCommitButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		amendPreviousCommitButton.setText(UIText.StagingView_Ammend_Previous_Commit);

		Button signedOffByButton = new Button(commitMessageComposite, SWT.CHECK);
		signedOffByButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		signedOffByButton.setText(UIText.StagingView_Add_Signed_Off_By);

		Button addChangeIdButton = new Button(commitMessageComposite, SWT.CHECK);
		GridData addChangeIdButtonGridData = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		addChangeIdButtonGridData.minimumHeight = 1;
		addChangeIdButton.setLayoutData(addChangeIdButtonGridData);
		addChangeIdButton.setText(UIText.StagingView_Add_Change_ID);

		commitButton = new Button(commitMessageComposite, SWT.NONE);
		commitButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		commitButton.setText(UIText.StagingView_Commit);

		Composite stagedComposite = new Composite(veriticalSashForm, SWT.NONE);
		stagedComposite.setLayout(new GridLayout(1, false));

		new Label(stagedComposite, SWT.NONE).setText(UIText.StagingView_StagedChanges);

		Composite stagedTableComposite = new Composite(stagedComposite, SWT.NONE);
		stagedTableComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		stagedTableComposite.setLayout(new TableColumnLayout());

		stagedTableViewer = new TableViewer(stagedTableComposite, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
