
		branchTree = new TreeViewer(parent, SWT.SINGLE | SWT.BORDER);
		new RepositoriesViewLabelProvider(branchTree);
		branchTree.setContentProvider(new RepositoriesViewContentProvider());

		GridDataFactory.fillDefaults().grab(true, true).hint(500, 300).applyTo(
				branchTree.getTree());
		branchTree.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
