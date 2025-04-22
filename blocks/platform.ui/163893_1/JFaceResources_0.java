		Supplier<URL> supplier = () -> {
			if (bundle != null) {
				URL url = FileLocator.find((Bundle) bundle, new Path(path), null);
				if (url != null)
					return url;
			}
			URL url = fallback.getResource(fallbackPath);
			return url;
		};

	imageRegistry.put(key, ImageDescriptor.createFromURLSupplier(false, supplier));
