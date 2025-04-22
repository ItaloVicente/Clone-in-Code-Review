		ImageDescriptor descriptor = null;

		if (bundle != null) {
			URL url = FileLocator.find((Bundle) bundle, new Path(path), null);
			if (url != null)
				descriptor = ImageDescriptor.createFromURL(url);
		}

		if (descriptor == null)
			descriptor = ImageDescriptor.createFromFile(fallback, fallbackPath);

		imageRegistry.put(key, descriptor);
