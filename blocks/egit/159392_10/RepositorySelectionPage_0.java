		Button browseLocalFolderButton = new Button(g, SWT.NULL);
		if (!sourceSelection) {
			GridDataFactory.fillDefaults().span(3, 1)
					.applyTo(browseLocalFolderButton);
		}
		browseLocalFolderButton.setText(UIText.RepositorySelectionPage_BrowseLocalFolder);
		browseLocalFolderButton.addSelectionListener(new SelectionAdapter() {
