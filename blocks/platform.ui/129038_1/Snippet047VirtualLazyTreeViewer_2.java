	public Snippet047VirtualLazyTreeViewer(Shell shell) {
		fText = new Text(shell, SWT.SINGLE | SWT.LEAD | SWT.BORDER);
		fText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		fText.setEnabled(false);

		fViewer = new TreeViewer(shell, SWT.VIRTUAL | SWT.BORDER);
		fViewer.setLabelProvider(new LabelProvider());
		fViewer.setContentProvider(new MyContentProvider());
		fViewer.setUseHashlookup(true);
		Node root = new Node(0, getRandomChildCount(), null);
		fViewer.setInput(root);
		fViewer.getTree().setLayoutData(GridDataFactory.fillDefaults().create());
		fViewer.setChildCount(root, root.getChildCount());
		fViewer.getTree().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
