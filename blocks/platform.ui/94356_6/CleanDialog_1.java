	private PatternFilter getPatternFilter() {
		return new PatternFilter() {
			@Override
			protected boolean isParentMatch(Viewer viewer, Object element) {
				if (!(element instanceof IProject)) {
					return false;
				}
				return super.isParentMatch(viewer, element);
			}

			@Override
			protected boolean isLeafMatch(Viewer viewer, Object element) {
				if (!(element instanceof IProject)) {
					return false;
				}
				return super.isLeafMatch(viewer, element);
			}
		};
	}

	private CheckboxTreeViewer createProjectSelectionTreeViewer(Composite parent, int style) {
		CheckboxTreeViewer treeViewer = new CheckboxTreeViewer(parent, style);
		treeViewer.setExpandPreCheckFilters(true);
		treeViewer.setContentProvider(new WorkbenchContentProvider());
		treeViewer.setLabelProvider(new WorkbenchLabelProvider());
		treeViewer.setComparator(new ResourceComparator(ResourceComparator.NAME));
		treeViewer.addFilter(new ViewerFilter() {
			private final IProject[] projectHolder = new IProject[1];

			@Override
