			final ResourceManager mgr;
			if (LazyResourceManager.getCacheSize() == 0) {
				mgr = new DeviceResourceManager(toQuery);
			} else {
				mgr = new LazyResourceManager(new DeviceResourceManager(toQuery));
			}
