		rightTree = new TreeViewer(mainSash, SWT.BORDER);
		rightTree.setContentProvider(new RightTreeContentProvider());
		GridDataFactory.fillDefaults().grab(true, true).applyTo(
				rightTree.getTree());

		rightTree.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				reactOnSelection(event);
			}
		});

		rightTree.addTreeListener(new ITreeViewerListener() {
			public void treeExpanded(TreeExpansionEvent event) {
				reactOnExpand(event);
			}

			public void treeCollapsed(TreeExpansionEvent event) {
				reactOnCollapse(event);
			}
		});

		rightTree.addOpenListener(new IOpenListener() {
			public void open(OpenEvent event) {
				reactOnOpen(event);
			}
		});
		leftTree.getTree().setEnabled(false);
		rightTree.getTree().setEnabled(false);
