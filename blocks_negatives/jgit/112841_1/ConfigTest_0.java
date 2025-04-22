	public static String pathToString(File file) {
		final String path = file.getPath();
		if (SystemReader.getInstance().isWindows()) {
			return path.replace('\\', '/');
		}
		return path;
	}

