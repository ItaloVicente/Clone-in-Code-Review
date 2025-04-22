	public Viewer findContentViewer(Viewer oldViewer, ICompareInput input,
			Composite parent) {
		Viewer newViewer = super.findContentViewer(oldViewer, input, parent);
		ToolBarManager manager = CompareViewerPane.getToolBarManager(parent);
		if (manager != null) {
			setToggleCurrentChangesAction(manager, newViewer, input);
