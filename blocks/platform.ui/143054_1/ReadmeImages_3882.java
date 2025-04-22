	private static ImageDescriptor createImageDescriptor(String path) {
		try {
			URL url = new URL(BASE_URL, path);
			return ImageDescriptor.createFromURL(url);
		} catch (MalformedURLException e) {
		}
		return ImageDescriptor.getMissingImageDescriptor();
	}
