	private static boolean isSymRef(byte[] ref) {
		if (ref.length < 9)
			return false;
				&& ref[7] == ' ';
	}

	private static File getSymRef(File workTree, File dotGit, FS fs)
			throws IOException {
		byte[] content = IO.readFully(dotGit);
		if (!isSymRef(content))
			throw new IOException(MessageFormat.format(
					JGitText.get().invalidGitdirRef, dotGit.getAbsolutePath()));

		int pathStart = 8;
		int lineEnd = RawParseUtils.nextLF(content, pathStart);
		while (content[lineEnd - 1] == '\n' ||
		       (content[lineEnd - 1] == '\r' && SystemReader.getInstance().isWindows()))
			lineEnd--;
		if (lineEnd == pathStart)
			throw new IOException(MessageFormat.format(
					JGitText.get().invalidGitdirRef, dotGit.getAbsolutePath()));

		String gitdirPath = RawParseUtils.decode(content, pathStart, lineEnd);
		File gitdirFile = fs.resolve(workTree, gitdirPath);
		if (gitdirFile.isAbsolute())
			return gitdirFile;
		else
			return new File(workTree, gitdirPath).getCanonicalFile();
	}
