	/*
	 * Strip the leading directories from the path
	 */
	private String stripPath(String path) {
		String pathOrig = new String(path);
		for (int i = 0; i < stripLevel; i++) {
			int firstSep = path.indexOf('/');
			if (firstSep == 0) {
				path = path.substring(1);
				firstSep = path.indexOf('/');
			}
			if (firstSep == -1) {
				return pathOrig;
			}
			path = path.substring(firstSep);
		}
		return path;
	}

