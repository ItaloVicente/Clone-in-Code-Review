
	private static String sanitizedPath(File file) {
		String path = file.getPath();
		if (File.separatorChar != '/') {
			path = path.replace(File.separatorChar
		}
		return path;
	}

