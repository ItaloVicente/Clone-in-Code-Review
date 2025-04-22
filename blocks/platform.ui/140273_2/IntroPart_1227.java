		Image image = JFaceResources.getResources().createImageWithDefault(imageDescriptor);
		titleImage = image;
	}

	protected void setTitleImage(Image titleImage) {
		Assert.isTrue(titleImage == null || !titleImage.isDisposed());
		if (this.titleImage == titleImage) {
