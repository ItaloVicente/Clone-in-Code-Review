
		deleteGitDir = new Button(main, SWT.CHECK);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(deleteGitDir);
		deleteGitDir
				.setText(UIText.DeleteRepositoryConfirmDialog_DeleteGitDirCheckbox);
		createIndentedLabel(main, repository.getDirectory().getPath());

		deleteWorkDir = new Button(main, SWT.CHECK);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(deleteWorkDir);
		deleteWorkDir
				.setText(UIText.DeleteRepositoryConfirmDialog_DeleteWorkingDirectoryCheckbox);
		deleteWorkDirLabel = createIndentedLabel(main, repository.getWorkTree()
				.getPath());

		removeProjects = new Button(main, SWT.CHECK);

		deleteGitDir.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shouldDeleteGitDir = deleteGitDir.getSelection();
				updateUI();
			}
		});

