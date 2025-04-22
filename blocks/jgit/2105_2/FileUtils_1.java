
	public static void createNewFile(File f) throws IOException {
		if (!f.createNewFile())
			throw new IOException(MessageFormat.format(
					JGitText.get().createNewFileFailed
	}
