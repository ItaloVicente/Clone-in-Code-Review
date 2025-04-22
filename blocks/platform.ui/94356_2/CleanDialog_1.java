	private CheckboxTreeViewer createProjectSelectionTreeViewer(Composite parent, int style) {
		CheckboxTreeViewer treeViewer = new CheckboxTreeViewer(parent, style);
		treeViewer.setExpandPreCheckFilters(true);
		treeViewer.setContentProvider(new WorkbenchContentProvider());
		treeViewer.setLabelProvider(new WorkbenchLabelProvider());
		treeViewer.setComparator(new ResourceComparator(ResourceComparator.NAME));
		treeViewer.addFilter(new ViewerFilter() {
			private final IProject[] projectHolder = new IProject[1];

			@Override
