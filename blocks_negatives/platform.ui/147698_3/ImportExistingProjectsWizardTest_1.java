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
