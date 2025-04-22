		createFilterText(container);

		return area;
	}

	@Override
	protected Control createContents(Composite parent) {
		Control contents = super.createContents(parent);
		checkInput();
		return contents;
	}

	private void createFilterText(Composite container) {
