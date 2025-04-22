		URL fullPathString = plugin.getBundle().getResource("/" + WS_DATA_PREFIX + "/" + dataLocation + ".zip");

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

		ZipFile zFile = new ZipFile(origin);

		File destination = new File(FileSystemHelper.getRandomLocation(
				FileSystemHelper.getTempDir()).toOSString());
		FileTool.unzip(zFile, destination);
		return destination.getAbsolutePath();
	}
