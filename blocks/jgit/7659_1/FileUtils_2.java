
	public static void renameFile(File from
		if (to.exists())
			throw new IOException(MessageFormat.format(
					JGitText.get().renameFileFailed
		if (!from.renameTo(to))
			throw new IOException(MessageFormat.format(
					JGitText.get().renameFileFailed

	}
