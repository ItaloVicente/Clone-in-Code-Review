	public static void rename(final File src
			throws IOException {
		int attempts = FS.DETECTED.retryFailedLockFileCommit() ? 10 : 1;
		while (--attempts >= 0) {
			if (src.renameTo(dst))
				return;
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				throw new IOException(MessageFormat.format(
						JGitText.get().renameFileFailed
						dst.getAbsolutePath()));
			}
		}
		throw new IOException(MessageFormat.format(
				JGitText.get().renameFileFailed
				dst.getAbsolutePath()));
	}

