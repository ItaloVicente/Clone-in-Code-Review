		final Button deleteGitDir = new Button(main, SWT.CHECK);
		deleteGitDir.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shouldDeleteGitDir = deleteGitDir.getSelection();
				setOkButtonEnablement();
			}
		});
		deleteGitDir.setSelection(shouldDeleteGitDir);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(deleteGitDir);
		deleteGitDir.setText(UIText.DeleteRepositoryConfirmDialog_DeleteGitDirCheckbox);
		createIndentedLabel(main, repository.getDirectory().getPath());

