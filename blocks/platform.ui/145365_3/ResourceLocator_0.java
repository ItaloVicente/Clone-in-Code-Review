	public static Optional<ImageDescriptor> imageDescriptorFromBundle(Class<?> classFromBundle, String imageFilePath) {
		Optional<URL> locate = locate(classFromBundle, imageFilePath);
		if (locate.isPresent()) {
			return Optional.of(ImageDescriptor.createFromURL(locate.get()));
		}
		return Optional.empty();
	}

