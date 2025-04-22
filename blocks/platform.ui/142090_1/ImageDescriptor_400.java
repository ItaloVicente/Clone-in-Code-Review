	public static ImageDescriptor createFromURL(URL url) {
		if (url == null) {
			return getMissingImageDescriptor();
		}
		return new URLImageDescriptor(url);
	}
