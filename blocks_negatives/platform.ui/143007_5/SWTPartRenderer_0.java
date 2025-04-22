		Image previouslyAdornedImage = (Image) element.getTransientData().get(
		if (previouslyAdornedImage != null
				&& !previouslyAdornedImage.isDisposed())
			previouslyAdornedImage.dispose();
		element.getTransientData().remove(IPresentationEngine.ADORNMENT_PIN);

