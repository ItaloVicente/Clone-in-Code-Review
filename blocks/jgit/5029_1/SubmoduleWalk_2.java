	public static File getSubmoduleGitDirectory(final File parent
			final String path) throws IOException {
		File gitDir = new File(new File(parent
		if (!gitDir.isFile())
			return gitDir;
		byte[] content = IO.readFully(gitDir);
		if (!isSymRef(content))
			throw new IOException(MessageFormat.format(
					JGitText.get().submoduleInvalidGitdirRef
		int pathStart = 8;
		int lineEnd = RawParseUtils.nextLF(content
		if (content[lineEnd - 1] == '\n')
			lineEnd--;
		if (lineEnd == pathStart)
			throw new IOException(MessageFormat.format(
					JGitText.get().submoduleInvalidGitdirRef

		String gitdirPath = RawParseUtils.decode(content
		if (!gitdirPath.startsWith("./") && !gitdirPath.startsWith("../"))
			return new File(gitdirPath);
		else
			return new File(new File(parent
					.getCanonicalFile();
	}

	private static boolean isSymRef(byte[] ref) {
		if (ref.length < 9)
			return false;
				&& ref[7] == ' ';
	}

