	}

	protected void createContents(Composite parent, int width, int height,
			int style) {
		Composite composite = new Composite(parent, style);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.makeColumnsEqualWidth = true;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		composite.setFont(parent.getFont());

		createTreeViewer(composite, width / 2, height);
		createListViewer(composite, width / 2, height);

		initialize();
	}

	protected void createListViewer(Composite parent, int width, int height) {
		listViewer = CheckboxTableViewer.newCheckList(parent, SWT.BORDER);
		GridData data = new GridData(GridData.FILL_BOTH);
		data.widthHint = width;
		data.heightHint = height;
		listViewer.getTable().setLayoutData(data);
		listViewer.getTable().setFont(parent.getFont());
		listViewer.setContentProvider(listContentProvider);
		listViewer.setLabelProvider(listLabelProvider);
		listViewer.setComparator(listComparator);
		listViewer.addCheckStateListener(this);
	}

	protected void createTreeViewer(Composite parent, int width, int height) {
		Tree tree = new Tree(parent, SWT.CHECK | SWT.BORDER);
		GridData data = new GridData(GridData.FILL_BOTH);
		data.widthHint = width;
		data.heightHint = height;
		tree.setLayoutData(data);
		tree.setFont(parent.getFont());

		treeViewer = new CheckboxTreeViewer(tree);
		treeViewer.setContentProvider(treeContentProvider);
		treeViewer.setLabelProvider(treeLabelProvider);
		treeViewer.setComparator(treeComparator);
		treeViewer.addTreeListener(this);
		treeViewer.addCheckStateListener(this);
		treeViewer.addSelectionChangedListener(this);
	}

	protected boolean determineShouldBeAtLeastGrayChecked(Object treeElement) {
		List checked = (List) checkedStateStore.get(treeElement);
		if (checked != null && (!checked.isEmpty())) {
