	private void setViewerFields(final TreeViewer viewer) {
		viewer.setLabelProvider(new LabelProvider());
		viewer.setContentProvider(new MyContentProvider());
		viewer.setInput(createModel());
		viewer.getTree().setToolTipText("");
	}
