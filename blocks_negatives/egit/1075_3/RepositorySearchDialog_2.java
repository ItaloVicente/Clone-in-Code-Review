		fTree = new FilteredTree(searchResultGroup, SWT.CHECK | SWT.BORDER,
				new PatternFilter());
		fTreeViewer = fTree.getViewer();
		fTreeViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {

					public void selectionChanged(SelectionChangedEvent event) {
						enableOk();
					}
				});
