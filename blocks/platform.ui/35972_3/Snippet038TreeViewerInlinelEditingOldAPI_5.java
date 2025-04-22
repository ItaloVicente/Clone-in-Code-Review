	}

	public Snippet038TreeViewerInlinelEditingOldAPI(Shell shell) {
		final TreeViewer viewer = new TreeViewer(shell, SWT.FULL_SELECTION);

		createColumnFor(viewer, "Column 1", 0);
		createColumnFor(viewer, "Column 2", 1);
