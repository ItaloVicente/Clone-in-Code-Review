		workDir.setEnabled(false);

		browseWorkDir = new Button(externalComposite, SWT.PUSH);
		browseWorkDir
				.setText(UIText.ExistingOrNewPage_BrowseRepositoryButton);
		browseWorkDir.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				File workspace = ResourcesPlugin.getWorkspace().getRoot()
						.getLocation().toFile();
				String result;
				DirectoryDialog dialog = new DirectoryDialog(getShell());
				dialog.setMessage(UIText.CreateRepositoryPage_PageMessage);
				if (workspace.exists() && workspace.isDirectory()) {
					dialog.setFilterPath(workspace.getPath());
				}
				result = dialog.open();
				if (result != null)
					workDir.setText(result);
			}
		});
