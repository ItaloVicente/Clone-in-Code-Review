		authorText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		amendPreviousCommitButton = new Button(commitMessageComposite,
				SWT.CHECK);
		amendPreviousCommitButton.setLayoutData(new GridData(SWT.LEFT,
				SWT.CENTER, false, false, 2, 1));
		amendPreviousCommitButton
				.setText(UIText.StagingView_Ammend_Previous_Commit);

		signedOffByButton = new Button(commitMessageComposite, SWT.CHECK);
		signedOffByButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER,
				false, false, 2, 1));
