		this.title = title;
		firePropertyChange(IWorkbenchPart.PROP_TITLE);
	}

	protected void setTitleImage(Image titleImage) {
		Assert.isTrue(titleImage == null || !titleImage.isDisposed());
		if (this.titleImage == titleImage) {
