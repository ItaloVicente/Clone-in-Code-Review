	}

	public Snippet047VirtualLazyTreeViewer(Shell shell) {
		final TreeViewer viewer = new TreeViewer(shell, SWT.VIRTUAL | SWT.BORDER);
		viewer.setLabelProvider(new LabelProvider());
		viewer.setContentProvider(new MyContentProvider(viewer));
		viewer.setUseHashlookup(true);
		IntermediateNode[] model = createModel();
		viewer.setInput(model);
		viewer.getTree().setItemCount(model.length);
