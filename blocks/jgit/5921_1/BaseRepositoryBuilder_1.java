	private static File getSymRef(File workTree
			throws IOException {
		byte[] content = IO.readFully(dotGit);
		if (!isSymRef(content))
			throw new IOException(MessageFormat.format(
					JGitText.get().invalidGitdirRef

		int pathStart = 8;
		int lineEnd = RawParseUtils.nextLF(content
		if (content[lineEnd - 1] == '\n')
			lineEnd--;
		if (lineEnd == pathStart)
			throw new IOException(MessageFormat.format(
					JGitText.get().invalidGitdirRef

		String gitdirPath = RawParseUtils.decode(content
		File gitdirFile = new File(gitdirPath);
		if (gitdirFile.isAbsolute())
			return gitdirFile;
		else
			return new File(workTree
	}

