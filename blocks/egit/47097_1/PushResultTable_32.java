	PushResultTable(final Composite parent,
			final IDialogSettings dialogSettings) {
		root = new SashForm(parent, SWT.VERTICAL);

		Composite treeContainer = new Composite(root, SWT.NONE);
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(treeContainer);
		treeViewer = new TreeViewer(treeContainer);
