
	public ImageDescriptor imageDescriptorFromURI(URI uriIconPath) {
		try {
			return ImageDescriptor.createFromURL(new URL(uriIconPath.toString()));
		} catch (MalformedURLException | NullPointerException e) {
			return getMissingImageDescriptor();
		}
	}

