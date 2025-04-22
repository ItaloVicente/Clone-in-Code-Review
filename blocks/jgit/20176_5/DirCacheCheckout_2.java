	public static void checkValidPath(String path) throws InvalidPathException {
		boolean isWindows = SystemReader.getInstance().isWindows();
		boolean isOSX = SystemReader.getInstance().isMacOS();
		boolean ignCase = isOSX || isWindows;

		byte[] bytes = Constants.encode(path);
		int segmentStart = 0;
		for (int i = 0; i < bytes.length; i++) {
			if (bytes[i] == '/') {
				checkValidPathSegment(isWindows
						i
				segmentStart = i + 1;
			}
		}
		if (segmentStart < bytes.length)
			checkValidPathSegment(isWindows
					bytes.length
	}

