		listViewer.addDoubleClickListener(event -> okPressed());
		return composite;
	}

	@Override
	protected Control createContents(Composite parent) {
		Control contents = super.createContents(parent);
