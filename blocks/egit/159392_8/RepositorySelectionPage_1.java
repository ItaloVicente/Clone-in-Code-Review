		if (sourceSelection) {
			Button browseLocalBundleFileButton = new Button(g, SWT.NULL);
			browseLocalBundleFileButton.setText(UIText.RepositorySelectionPage_BrowseLocalBundleFile);
			browseLocalBundleFileButton.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent evt) {
					FileDialog dialog = new FileDialog(getShell());
					dialog.setText(UIText.RepositorySelectionPage_sourceSelectionTitle);
					dialog.setFilterPath(getUriFilterPath());

					String result = dialog.open();
					if (result != null) {
								uriText.setText(Protocol.FILE.getDefaultScheme()
										+ "://" + result); //$NON-NLS-1$
					}
				}
			});
		}
