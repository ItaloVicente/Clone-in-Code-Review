	public static String pathTail(String path) {
		final int slash = path.lastIndexOf('/');
		return slash >= 0 ? path.substring(slash + 1) : path;
	}

	public static String pathTrimLeadingSlash(String path) {
		return path.length() > 0 && path.charAt(0) == '/' ? path.substring(1)
				: path;
	}

	public static String pathAddTrailingSlash(String path) {
		return path.length() == 0 || path.charAt(path.length() - 1) != '/' ? path
				+ "/"
				: path;
	}

