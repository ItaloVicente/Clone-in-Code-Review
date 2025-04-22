				setNeedsSearch();
			}

		});

		searchButton = new Button(searchGroup, SWT.PUSH);
		GridDataFactory.fillDefaults().align(SWT.BEGINNING, SWT.CENTER).span(3, 1).applyTo(searchButton);
		searchButton.setText(UIText.RepositorySearchDialog_Search);
		searchButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
