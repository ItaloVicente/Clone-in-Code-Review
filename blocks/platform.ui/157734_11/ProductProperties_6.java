	public static Optional<ImageDescriptor> aboutImage(Optional<IProduct> product) {
		if (product.isPresent()) {
			return getImage(product.get().getProperty(ABOUT_IMAGE), product.get().getDefiningBundle());
		} else {
			return Optional.empty();
		}
