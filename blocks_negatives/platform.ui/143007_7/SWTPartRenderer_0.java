	private Image adornImage(MUIElement element, Image image) {
		Image previouslyAdornedImage = (Image) element.getTransientData().get(
		if (previouslyAdornedImage != null
				&& !previouslyAdornedImage.isDisposed())
			previouslyAdornedImage.dispose();
		element.getTransientData().remove(IPresentationEngine.ADORNMENT_PIN);

		Image adornedImage = image;
		if (element.getTags().contains(IPresentationEngine.ADORNMENT_PIN)) {
			adornedImage = resUtils.adornImage(image, pinImage);
			if (adornedImage != image)
				element.getTransientData().put(
						"previouslyAdorned", adornedImage); //$NON-NLS-1$
