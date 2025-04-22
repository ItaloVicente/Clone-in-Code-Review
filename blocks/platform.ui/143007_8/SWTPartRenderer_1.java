	private void disposeAdornedImage(MUIElement element) {
		Image previouslyAdornedImage = (Image) element.getTransientData().get(ADORN_ICON_IMAGE_KEY);
		if (previouslyAdornedImage != null) {
			previouslyAdornedImage.dispose();
			previouslyAdornedImage = null;
		}
	}

	protected boolean imageChanged() {
		return false;
	}
