
	public static String getHumanishName(String aPath) {
		if ("".equals(aPath)) {
			throw new IllegalArgumentException();
		}
		String path = aPath;
		int s = path.lastIndexOf('/');
		if (s != -1)
			path = path.substring(s + 1);
			if (path.equals(DOT_GIT))
				throw new IllegalArgumentException();
			path = path.substring(0
		}
		return path;
	}
