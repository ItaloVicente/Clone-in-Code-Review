	private static String pathToString(File file) {
		final String path = file.getPath();
		return SystemReader.getInstance().isWindows() ? path.replace('\\'
				: path;
	}

