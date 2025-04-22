	public Snippet023TreeViewerCustomTooltips(Shell shell) {
		final TreeViewer viewer = new TreeViewer(shell);
		setViewerFields(viewer);

		final Listener labelListener = createLabelListenerFor(viewer);
		Listener treeListener = createTreeListenerFor(viewer, labelListener);
