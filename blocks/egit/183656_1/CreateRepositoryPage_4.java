		Label workingTreeLabel = new Label(main, SWT.NONE);
		workingTreeLabel.setText(UIText.CreateRepositoryPage_WorkingTreeLabel);
		workingTreeText = new Text(main, SWT.BORDER);
		workingTreeText.setText(initialDirectory);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER)
				.grab(true, false).applyTo(workingTreeText);
		workingTreeText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				checkPage();
			}
		});
		workingTreeBrowseButton = new Button(main, SWT.PUSH);
		workingTreeBrowseButton
				.setText(UIText.CreateRepositoryPage_BrowseButton);
		workingTreeBrowseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				File workspace = ResourcesPlugin.getWorkspace().getRoot()
						.getLocation().toFile();
				String result;
				DirectoryDialog dialog = new DirectoryDialog(getShell());
				dialog.setMessage(
						UIText.CreateRepositoryPage_WorkingTreeDialogMessage);
				if (workspace.exists() && workspace.isDirectory()) {
					dialog.setFilterPath(workspace.getPath());
				}
				result = dialog.open();
				if (result != null)
					workingTreeText.setText(result);
				updateControls();
			}
		});
		workingTreeText.setEnabled(false);
		workingTreeBrowseButton.setEnabled(false);

