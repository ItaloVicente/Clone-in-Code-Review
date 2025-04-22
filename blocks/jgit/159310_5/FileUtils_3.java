			handleDeleteException(f
		}
	}

	private static void handleDeleteException(File f
			int allOptions
		if (e != null && (allOptions & checkOptions) == 0) {
			throw new IOException(MessageFormat.format(
					JGitText.get().deleteFileFailed
