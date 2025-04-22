
		URL fullPathString = FileLocator.find(url);
		if (fullPathString == null) {
			try {
				url = new URL(imageFilePath);
			} catch (MalformedURLException e) {
				return null;
			}
		}
		return ImageDescriptor.createFromURL(url);
