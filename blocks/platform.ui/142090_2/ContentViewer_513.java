	public void setContentProvider(IContentProvider contentProvider) {
		Assert.isNotNull(contentProvider);
		IContentProvider oldContentProvider = this.contentProvider;
		this.contentProvider = contentProvider;
		if (oldContentProvider != null) {
			Object currentInput = getInput();
			oldContentProvider.inputChanged(this, currentInput, null);
			oldContentProvider.dispose();
			contentProvider.inputChanged(this, null, currentInput);
			refresh();
		}
	}
