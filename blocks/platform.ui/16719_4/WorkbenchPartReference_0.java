
		Image newLegacyPartImage = null;
		if (legacyPart != null) {
			newLegacyPartImage = legacyPart.getTitleImage();
		}
		if ((newLegacyPartImage != null) && (newLegacyPartImage != legacyPartImage)) {
			legacyPartImage = newLegacyPartImage;
			setImageDescriptor(computeImageDescriptor());
		}
		if (image == null) {
			image = JFaceResources.getResources().createImageWithDefault(imageDescriptor);
		}
