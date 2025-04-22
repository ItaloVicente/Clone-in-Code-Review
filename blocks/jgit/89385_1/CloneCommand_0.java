	private static boolean isDirExistsNotEmpty(File dir) {
		if (dir != null && dir.exists()) {
			File[] files = dir.listFiles();
			return files != null && files.length != 0;
		}
		return false;
	}

