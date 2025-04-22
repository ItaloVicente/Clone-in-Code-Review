		treePanel = new Composite(parent, SWT.NONE);
		treePanel.setLayout(new GridLayout());
		treeViewer = new TreeViewer(treePanel);
		treeViewer.setAutoExpandLevel(2);
		final IStyledLabelProvider styleProvider = new IStyledLabelProvider() {

			private final WorkbenchLabelProvider wrapped = new WorkbenchLabelProvider();
