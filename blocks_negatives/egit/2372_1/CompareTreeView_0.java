		SashForm mainSash = new SashForm(main, SWT.HORIZONTAL);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(mainSash);

		leftTree = new TreeViewer(mainSash, SWT.BORDER);
		leftTree.setContentProvider(new LeftTreeContentProvider());
		GridDataFactory.fillDefaults().grab(true, true).applyTo(
				leftTree.getTree());

		leftTree.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				reactOnSelection(event);
			}
		});

		leftTree.addTreeListener(new ITreeViewerListener() {
			public void treeExpanded(TreeExpansionEvent event) {
				reactOnExpand(event);
			}

			public void treeCollapsed(TreeExpansionEvent event) {
				reactOnCollapse(event);
			}
		});

		leftTree.addOpenListener(new IOpenListener() {
			public void open(OpenEvent event) {
				reactOnOpen(event);
			}
		});
		rightTree = new TreeViewer(mainSash, SWT.BORDER);
		rightTree.setContentProvider(new RightTreeContentProvider());
		GridDataFactory.fillDefaults().grab(true, true).applyTo(
				rightTree.getTree());

		rightTree.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				reactOnSelection(event);
			}
		});
