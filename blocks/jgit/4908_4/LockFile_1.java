	public static boolean unlock(final File file) {
		final File lockFile = getLockFile(file);
		final int flags = FileUtils.RETRY | FileUtils.SKIP_MISSING;
		try {
			FileUtils.delete(lockFile
		} catch (IOException ignored) {
		}
		return !lockFile.exists();
	}

	static File getLockFile(File file) {
		return new File(file.getParentFile()
	}

