		searchButton = new Button(searchGroup, SWT.PUSH);
		GridDataFactory.fillDefaults().span(3, 1)
				.align(SWT.BEGINNING, SWT.FILL).applyTo(searchButton);
		searchButton.setText(UIText.RepositorySearchDialog_Search);
		searchButton
				.setToolTipText(UIText.RepositorySearchDialog_SearchTooltip);
		searchButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				doSearch();
			}
		});


