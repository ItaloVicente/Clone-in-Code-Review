		Button browseButton = new Button(g, SWT.NULL);
		browseButton.setText(UIText.RepositorySelectionPage_BrowseLocalFile);
		browseButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dialog = new DirectoryDialog(getShell());
				if (!uriText.getText().equals("")) { //$NON-NLS-1$
					try {
						URI testUri = URI.create(uriText.getText().replace(
								'\\', '/'));
						if (testUri.getScheme().equals("file")) { //$NON-NLS-1$
							String path = testUri.getPath();
							if (path.length() > 1 && path.startsWith("/")) //$NON-NLS-1$
								path = path.substring(1);

							dialog.setFilterPath(path);
						}
					} catch (IllegalArgumentException e1) {
					}

				}
				String result = dialog.open();
				if (result != null)
					uriText.setText("file:///" + result); //$NON-NLS-1$
			}

		});

