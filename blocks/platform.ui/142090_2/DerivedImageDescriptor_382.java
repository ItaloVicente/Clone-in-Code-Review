	private final Image internalCreateImage(Device device) {
		Image originalImage = original.createImage(device);
		Image result = new Image(device, originalImage, flags);
		original.destroyResource(originalImage);
		return result;
	}
