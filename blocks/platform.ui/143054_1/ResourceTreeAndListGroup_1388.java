		listViewer.setUseHashlookup(true);
		listViewer.getTable().setLayoutData(data);
		listViewer.getTable().setFont(parent.getFont());
		listViewer.setContentProvider(listContentProvider);
		listViewer.setLabelProvider(listLabelProvider);
		listViewer.addCheckStateListener(checkListener);
	}

	private void createTreeViewer(Composite parent, boolean useHeightHint) {
		Tree tree = new Tree(parent, SWT.CHECK | SWT.BORDER);
		GridData data = new GridData(GridData.FILL_BOTH);
		if (useHeightHint) {
