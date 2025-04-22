	private static boolean isEmptyDir(final File dir) {
		if (!dir.exists())
			return true;
		if (!dir.isDirectory())
			return false;
		return dir.listFiles().length == 0;
	}

