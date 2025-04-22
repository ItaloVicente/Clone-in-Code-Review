	private Image getImage(ImageDescriptor imageDescriptor) {
		if (!images.containsKey(imageDescriptor)) {
			images.put(imageDescriptor, imageDescriptor.createImage());
		}
		return images.get(imageDescriptor);
	}

