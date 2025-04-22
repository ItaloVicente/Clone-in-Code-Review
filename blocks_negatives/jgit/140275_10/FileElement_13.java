	private static File getTempFile(final String path, File workingDir,
			String midName) throws IOException {
		return getTempFile(new File(path), workingDir, midName);
	}

	/**
	 * Deletes and invalidates temporary file if necessary.
	 */
	public void cleanTemporaries() {
		if (tempFile != null && tempFile.exists())
		tempFile.delete();
		tempFile = null;
	}

	private static File copyFromStream(final File file,
