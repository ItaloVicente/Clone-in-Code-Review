	@Override
	protected Control createContents(Composite parent) {
		Control contents = super.createContents(parent);
		listViewer.setSelection(new StructuredSelection(getInitialSelection()), true);
		return contents;
	}
