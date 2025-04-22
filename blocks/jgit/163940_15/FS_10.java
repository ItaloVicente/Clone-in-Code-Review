	private static boolean isSymRef(byte[] ref) {
		if (ref.length < 9)
			return false;
				&& ref[7] == ' ';
	}

	public File getSymRef(File workTree
			throws IOException {
		byte[] content = IO.readFully(dotGit);
		if (!isSymRef(content))
			throw new IOException(MessageFormat.format(
					JGitText.get().invalidGitdirRef

		int pathStart = 8;
		int lineEnd = RawParseUtils.nextLF(content
		while (content[lineEnd - 1] == '\n' || (content[lineEnd - 1] == '\r'
				&& SystemReader.getInstance().isWindows()))
			lineEnd--;
		if (lineEnd == pathStart)
			throw new IOException(MessageFormat.format(
					JGitText.get().invalidGitdirRef

		String gitdirPath = RawParseUtils.decode(content
		File gitdirFile = resolve(workTree
		if (gitdirFile.isAbsolute())
			return gitdirFile;
		return new File(workTree
	}

	public File getCommonDir(File dir) throws IOException {
		File commonDir = null;
		File commonDirFile = new File(dir
		if (commonDirFile.isFile()) {
			String commonDirPath = new String(IO.readFully(commonDirFile))
					.trim();
			commonDir = new File(commonDirPath);
			if (!commonDir.isAbsolute()) {
				commonDir = new File(dir
			}
		}
		return commonDir;
	}

