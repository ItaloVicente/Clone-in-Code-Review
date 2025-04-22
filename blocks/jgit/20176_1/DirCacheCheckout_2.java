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
		int p0 = 0;
		int p1;
		L0: while (p0 < bytes.length) {
			p1 = p0 + 1;
			while (p1 < bytes.length) {
				if (bytes[p1] == '/') {
					checkValidPathSegment(isWindows
							path);
					p0 = p1 + 1;
					continue L0;
				}
				p1++;
			}
			checkValidPathSegment(isWindows
			p0 = p1;
		}
	}

