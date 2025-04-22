
	@Override
	public Image createImage(boolean returnMissingImageOnError, Device device) {
		URL url = supplier.get();
		if (url == null) {
			return returnMissingImageOnError ? ImageDescriptor.getMissingImageDescriptor().createImage() : null;
		}
		return ImageDescriptor.createFromURL(url).createImage(returnMissingImageOnError, device);
	}
