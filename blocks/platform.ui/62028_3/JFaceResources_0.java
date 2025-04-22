		imageRegistry.put(key, ImageDescriptor.createFromSupplier(() -> {
			if (bundle != null) {
				URL url = FileLocator.find((Bundle) bundle, new Path(path), null);
				if (url != null)
					return ImageDescriptor.createFromURL(url).getImageData();
			}
			return ImageDescriptor.createFromFile(fallback, fallbackPath).getImageData();
		}));
