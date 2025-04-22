		searchButton = new Button(buttonColumn, SWT.PUSH);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).applyTo(
				searchButton);
		searchButton.setText(UIText.RepositorySearchDialog_Search);
		searchButton
				.setToolTipText(UIText.RepositorySearchDialog_SearchTooltip);
		searchButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				doSearch();
			}
		});

