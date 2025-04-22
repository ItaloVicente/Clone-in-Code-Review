			Image previousImage = (Image) element.getTransientData().get(ADORN_ICON_IMAGE_KEY);
			boolean exist = previousImage != null && !previousImage.isDisposed(); // Cached image exist
			if (!exist) {
				Image adornedImage = resUtils.adornImage(image, pinImage);
				if (adornedImage != image) {
					element.getTransientData().put(ADORN_ICON_IMAGE_KEY, adornedImage);
				}
				return adornedImage;
			}
			return previousImage;
