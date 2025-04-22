	public static File createTempDir(String prefix
			throws IOException {
		for (int i = 0; i < RETRIES; i++) {
			File tmp = File.createTempFile(prefix
			if (!tmp.delete()) {
				continue;
			}
			if (!tmp.mkdir()) {
				continue;
			}
			return tmp;
		}
		throw new IOException(JGitText.get().cannotCreateTempDir);
	}

