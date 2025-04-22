	private Image getLabelImage(String iconURI) {
		Image image = images.get(iconURI);
		if (image == null) {
			ImageDescriptor descriptor = utils.imageDescriptorFromURI(URI
					.createURI(iconURI));
			image = descriptor.createImage();
			images.put(iconURI, image);
		}
		return image;
	}

