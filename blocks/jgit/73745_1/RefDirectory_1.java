	private static void delete(final File file
			throws IOException {
		if (!file.delete() && file.isFile()) {
			throw new IOException(MessageFormat.format(
					JGitText.get().fileCannotBeDeleted
		}

		if (depth > 0 && rLck != null) {
		}
