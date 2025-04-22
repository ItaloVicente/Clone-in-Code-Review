	/**
	 * Create a new iterator to traverse a subdirectory.
	 *
	 * @param p
	 *            the parent iterator we were created from.
	 * @param root
	 *            the subdirectory. This should be a directory contained within
	 *            the parent directory.
	 * @param fs
	 *            the file system abstraction which will be necessary to perform
	 *            certain file system operations.
	 * @since 4.3
	 * @deprecated use {@link #FileTreeIterator(FileTreeIterator, File, FS)}
	 *             instead.
	 */
	@Deprecated
	protected FileTreeIterator(final WorkingTreeIterator p, final File root,
			FS fs) {
		this(p, root, fs, DefaultFileModeStrategy.INSTANCE);
	}

