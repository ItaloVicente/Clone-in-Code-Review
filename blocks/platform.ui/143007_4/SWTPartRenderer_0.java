		boolean isPinned = element.getTags().contains(IPresentationEngine.ADORNMENT_PIN);
		if (!isPinned) {
			Image previouslyAdornedImage = (Image) element.getTransientData().get("previouslyAdorned"); //$NON-NLS-1$
			if (previouslyAdornedImage != null && !previouslyAdornedImage.isDisposed())
				previouslyAdornedImage.dispose();
			element.getTransientData().remove(IPresentationEngine.ADORNMENT_PIN);
		} else {
