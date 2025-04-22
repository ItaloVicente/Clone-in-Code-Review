
	@Deprecated
	public static String relativize(String base
		return relativizeNativePath(base
	}

	public static String relativizeNativePath(String base
		return FS.DETECTED.relativize(base
	}

	public static String relativizeGitPath(String base
		return relativizePath(base
	}


