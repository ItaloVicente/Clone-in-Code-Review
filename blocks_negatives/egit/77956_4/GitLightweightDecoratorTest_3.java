	protected static boolean equals(ImageDescriptor left,
			ImageDescriptor right) {
		ImageDescriptor leftDescriptor = unwrapImageDescriptor(left);
		ImageDescriptor rightDescriptor = unwrapImageDescriptor(right);
		return Objects.equals(leftDescriptor, rightDescriptor);
	}

	protected static ImageDescriptor unwrapImageDescriptor(
			ImageDescriptor descriptor) {
		if (descriptor instanceof CachedImageDescriptor) {
			return ((CachedImageDescriptor) descriptor).getImageDescriptor();
		}
		return descriptor;
	}

