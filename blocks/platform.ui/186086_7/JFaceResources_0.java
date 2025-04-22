			final ResourceManager mgr;
			if (cacheSize == 0) {
				mgr = new DeviceResourceManager(toQuery);
			} else {
				mgr = new LazyResourceManager(cacheSize, new DeviceResourceManager(toQuery));
			}
