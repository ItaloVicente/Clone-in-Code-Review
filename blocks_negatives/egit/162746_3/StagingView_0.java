		this.commitAndPushButton = toolkit.createButton(commitButtonsContainer,
				UIText.StagingView_CommitAndPush, SWT.PUSH);
		commitAndPushButton.setImage(getImage(UIIcons.PUSH));
		commitAndPushButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				commit(true);
			}
		});
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER)
				.applyTo(commitAndPushButton);

