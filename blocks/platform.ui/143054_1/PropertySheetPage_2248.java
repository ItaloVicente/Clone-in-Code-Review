		viewer.getControl().setFocus();
	}

	public void setPropertySourceProvider(IPropertySourceProvider newProvider) {
		provider = newProvider;
		if (rootEntry instanceof PropertySheetEntry) {
			((PropertySheetEntry) rootEntry)
					.setPropertySourceProvider(provider);
