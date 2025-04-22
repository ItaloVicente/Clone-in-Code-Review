	public static Optional<ImageDescriptor> getAboutImage(Optional<IProduct> product) {
		if (product.isPresent()) {
			return getImage(product.get().getProperty(ABOUT_IMAGE), product.get().getDefiningBundle());
		} else {
			return Optional.empty();
		}
