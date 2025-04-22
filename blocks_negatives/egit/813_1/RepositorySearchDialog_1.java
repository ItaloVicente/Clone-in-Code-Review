		fTree = new FilteredTree(main, SWT.CHECK | SWT.BORDER,
				new PatternFilter());
		fTreeViewer = fTree.getViewer();
		fTreeViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {

					public void selectionChanged(SelectionChangedEvent event) {
						getButton(OK).setEnabled(hasCheckedItems());
					}
				});

		GridDataFactory.fillDefaults().grab(true, true).span(4, 1).minSize(0,
				300).applyTo(fTree);
		fTree.setEnabled(false);

		lookForNestedButton = new Button(main, SWT.CHECK);
