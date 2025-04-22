		if (image == null) {
			if (legacyPart != null) {
				image = legacyPart.getTitleImage();
			}
			if (image == null) {
				image = JFaceResources.getResources().createImageWithDefault(imageDescriptor);
			}
		}
