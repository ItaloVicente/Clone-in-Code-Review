	@Override
	public Viewer createViewer(Composite parent) {
		TableViewer viewer = new TableViewer(parent);
		viewer.setContentProvider(new TestModelContentProvider());
		viewer.setLabelProvider(new TestLabelProvider());
		viewer.getTable().setLinesVisible(true);
