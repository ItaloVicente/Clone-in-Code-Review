	/**
	 * Create a new lock for any file.
	 *
	 * @param f
	 *            the file that will be locked.
	 * @param fs
	 *            the file system abstraction which will be necessary to perform
	 *            certain file system operations.
	 * @deprecated use
	 *             {@link org.eclipse.jgit.internal.storage.file.LockFile#LockFile(File)}
	 *             instead
	 */
	@Deprecated
	public LockFile(final File f, final FS fs) {
		ref = f;
		lck = getLockFile(ref);
	}

