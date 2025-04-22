
	private static String sanitizePath(String path) {
		if (File.separatorChar != '/') {
			path = path.replace(File.separatorChar
		}
		return path;
	}

