
	public ImageDescriptor imageDescriptorFromURI(URI uriIconPath) {
		try {
			return ImageDescriptor.createFromURL(new URL(uriIconPath.toString()));
		} catch (MalformedURLException e) {
			return getMissingImageDescriptor();
		}
	}

