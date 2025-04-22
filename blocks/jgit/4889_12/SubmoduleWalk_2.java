	public static File getSubmoduleGitDirectory(final File parent
			final String path) throws IOException {
		File gitDir = new File(new File(parent
		if (gitDir.isFile()) {
			byte[] content = IO.readFully(gitDir);
			if (isSymRef(content)) {
				int pathStart = 8;
				int lineEnd = RawParseUtils.nextLF(content
				if (content[lineEnd - 1] == '\n')
					lineEnd--;
				if (lineEnd == pathStart)
					throw new IOException(MessageFormat.format(
							JGitText.get().submoduleInvalidGitdirRef
				gitDir = new File(RawParseUtils.decode(content
						lineEnd));
			} else
				throw new IOException(MessageFormat.format(
						JGitText.get().submoduleInvalidGitdirRef
		}
		return gitDir;
	}

	private static boolean isSymRef(byte[] ref) {
		if (ref.length < 9)
			return false;
				&& ref[7] == ' ';
	}

