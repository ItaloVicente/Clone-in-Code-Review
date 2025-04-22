		TestPlugin plugin = TestPlugin.getDefault();
		if (plugin == null) {
			throw new IllegalStateException(
					"TestPlugin default reference is null");
		}

		URL fullPathString = plugin.getBundle().getResource(WS_DATA_PREFIX + "/" + zipLocation + ".zip");

		URI fileURI = null;
		try {
			fileURI = FileLocator.resolve(fullPathString).toURI();
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException();
		}

		File origin = new File(fileURI);
		if (!origin.exists()) {
			throw new IllegalArgumentException();
		}

		File destination = new File(FileSystemHelper.getRandomLocation(
				FileSystemHelper.getTempDir()).toOSString()
				+ File.separator + ARCHIVE_HELLOWORLD + ".zip");
		FileTool.copy(origin, destination);
		return destination.getAbsolutePath();
