	public static void checkValidPath(String path) throws InvalidPathException {
		boolean isWindows = SystemReader.getInstance().isWindows();
		boolean isOSX = SystemReader.getInstance().isMacOS();
		boolean ignCase = isOSX || isWindows;

		byte[] bytes;
		try {
			bytes = path.getBytes(Constants.CHARACTER_ENCODING);
		} catch (UnsupportedEncodingException e) {
			throw new Error(e);
		}
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

