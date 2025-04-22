	private static String nameWithDotGit(String name) {
		if (name.endsWith(Constants.DOT_GIT_EXT))
			return name;
		return name + Constants.DOT_GIT_EXT;
	}

