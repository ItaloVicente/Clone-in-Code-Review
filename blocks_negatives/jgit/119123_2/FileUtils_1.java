
	/**
	 * Relativize a path
	 *
	 * @deprecated Use the more-clearly-named
	 *             {@link org.eclipse.jgit.util.FileUtils#relativizeNativePath(String, String)}
	 *             instead, or directly call
	 *             {@link org.eclipse.jgit.util.FileUtils#relativizePath(String, String, String, boolean)}
	 *
	 *             Expresses <code>other</code> as a relative file path from
	 *             <code>base</code>. File-separator and case sensitivity are
	 *             based on the current file system.
	 *
	 *             See also
	 *             {@link org.eclipse.jgit.util.FileUtils#relativizePath(String, String, String, boolean)}.
	 * @param base
	 *            Base path
	 * @param other
	 *            Destination path
	 * @return Relative path from <code>base</code> to <code>other</code>
	 * @since 3.7
	 */
	@Deprecated
	public static String relativize(String base, String other) {
		return relativizeNativePath(base, other);
	}

