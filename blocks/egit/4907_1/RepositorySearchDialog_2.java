		searchButton = new Button(searchGroup, SWT.PUSH);
		searchButton.setText(UIText.RepositorySearchDialog_Search);
		searchButton
				.setToolTipText(UIText.RepositorySearchDialog_SearchTooltip);
		searchButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				doSearch();
			}
		});

