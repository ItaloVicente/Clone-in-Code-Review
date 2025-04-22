		Group searchResultGroup = new Group(main, SWT.SHADOW_ETCHED_IN);
		searchResultGroup
				.setText(UIText.RepositorySearchDialog_SearchResultGroup);
		searchResultGroup.setLayout(new GridLayout(2, false));
		GridDataFactory.fillDefaults().applyTo(searchResultGroup);

		fTree = new FilteredTree(searchResultGroup, SWT.CHECK | SWT.BORDER,
				new PatternFilter());
		fTreeViewer = fTree.getViewer();
		fTreeViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {

					public void selectionChanged(SelectionChangedEvent event) {
						enableOk();
					}
				});

		GridDataFactory.fillDefaults().grab(true, true).minSize(0, 300)
				.applyTo(fTree);

		Composite buttonColumn = new Composite(searchResultGroup, SWT.NONE);
		buttonColumn.setLayout(new GridLayout(1, false));
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).applyTo(
				buttonColumn);

		searchButton = new Button(buttonColumn, SWT.PUSH);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).applyTo(
				searchButton);
		searchButton.setText(UIText.RepositorySearchDialog_Search);
		searchButton
				.setToolTipText(UIText.RepositorySearchDialog_SearchTooltip);
		searchButton.addSelectionListener(new SelectionAdapter() {
