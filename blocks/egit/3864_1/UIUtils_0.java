	public static Point getSize(ImageDescriptor descriptor) {
		ImageData data = descriptor.getImageData();
		if (data == null)
			return new Point(0, 0);
		return new Point(data.width, data.height);
	}
