		if (pluginId == null || imageFilePath == null) {
			throw new IllegalArgumentException();
		}

		IWorkbench workbench = PlatformUI.isWorkbenchRunning() ? PlatformUI.getWorkbench() : null;
		ImageDescriptor imageDescriptor = workbench == null ? null
				: workbench.getSharedImages().getImageDescriptor(imageFilePath);
		if (imageDescriptor != null)

		Bundle bundle = Platform.getBundle(pluginId);
		if (!BundleUtility.isReady(bundle)) {
			return null;
		}

		URL url;
		try {
			URI uri = new URI("platform", null, uriPath.toString(), null); //$NON-NLS-1$
			url = uri.toURL();
		} catch (MalformedURLException | URISyntaxException e) {
			return null;
		}

		URL fullPathString = FileLocator.find(url);
		if (fullPathString == null) {
			try {
				url = new URL(imageFilePath);
			} catch (MalformedURLException e) {
				return null;
			}
		}
		return ImageDescriptor.createFromURL(url);
