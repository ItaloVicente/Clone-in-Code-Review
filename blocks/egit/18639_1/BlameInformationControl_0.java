		Composite commitHeader = new Composite(displayArea, SWT.NONE);
		commitHeader.setLayout(GridLayoutFactory.fillDefaults().numColumns(3)
				.create());
		GridDataFactory.fillDefaults().grab(true, false).applyTo(commitHeader);

		commitLabel = new Label(commitHeader, SWT.READ_ONLY);
		commitLabel.setFont(JFaceResources.getBannerFont());

		Link openCommitLink = new Link(commitHeader, SWT.NONE);
		openCommitLink.setText(UIText.BlameInformationControl_OpenCommitLink);
		openCommitLink.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				openCommit();
			}
		});

		Link showInHistoryLink = new Link(commitHeader, SWT.NONE);
		showInHistoryLink.setText(UIText.BlameInformationControl_ShowInHistoryLink);
		showInHistoryLink.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				showCommitInHistory();
