	private Image adornImage(MUIElement element, Image image, boolean imageChanged) {
		if (element.getTags().contains(IPresentationEngine.ADORNMENT_PIN)) {// Only if Pinned
			Image previousImage = (Image) element.getTransientData().get(ADORN_ICON_IMAGE_KEY);
			boolean exist = previousImage != null && !previousImage.isDisposed(); // Cached image exist
			if (imageChanged || !exist) {
				if (imageChanged && exist) {
					disposeAdornedImage(element);// Need to dispose old image.If image changed
				}
				Image adornedImage = resUtils.adornImage(image, pinImage);
				if (adornedImage != image) {
					element.getTransientData().put(ADORN_ICON_IMAGE_KEY, adornedImage);
				}
				return adornedImage;
			}
			return previousImage;
