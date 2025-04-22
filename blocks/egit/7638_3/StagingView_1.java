		Composite buttonsContainer = toolkit.createComposite(composite);
		GridDataFactory.fillDefaults().grab(true, false).span(2,1).indent(0, 8).applyTo(buttonsContainer);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(buttonsContainer);

		this.commitButton = toolkit.createButton(buttonsContainer,
				UIText.StagingView_Commit, SWT.PUSH);
		commitButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				commit(false);
			}
		});
		GridDataFactory.fillDefaults().hint(100, SWT.DEFAULT)
				.align(SWT.RIGHT, SWT.CENTER).applyTo(commitButton);

		this.commitAndPushButton = toolkit.createButton(buttonsContainer,
				UIText.StagingView_CommitAndPush, SWT.PUSH);
		commitAndPushButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				commit(true);
			}
		});
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER)
				.applyTo(commitAndPushButton);

