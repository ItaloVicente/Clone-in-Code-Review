	@Override
	protected Control createContents(Composite parent) {
		Control contents = super.createContents(parent);
		updateButtonsEnablement();
		return contents;
	}

