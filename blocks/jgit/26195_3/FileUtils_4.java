
	public static File createTempDir(String prefix
			int retry) throws IOException {
		File tmp;
		int i = 0;
		while ((retry < 0) || (i < retry)) {
			i++;
			if (dir == null) {
				tmp = File.createTempFile(prefix
			} else {
				tmp = File.createTempFile(prefix
			}
			if (!tmp.delete())
				continue;
			try {
				mkdirs(tmp);
			} catch (IOException e) {
				continue;
			}
			return tmp;
		}
		throw new IOException(MessageFormat.format(
				JGitText.get().cannotCreateTempDir
	}
