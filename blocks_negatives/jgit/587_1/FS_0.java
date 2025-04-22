	public static File resolve(final File dir, final String name) {
		return INSTANCE.resolveImpl(dir, name);
	}

	/**
	 * Resolve this file to its actual path name that the JRE can use.
	 * <p>
	 * This method can be relatively expensive. Computing a translation may
	 * require forking an external process per path name translated. Callers
	 * should try to minimize the number of translations necessary by caching
	 * the results.
	 * <p>
	 * Not all platforms and JREs require path name translation. Currently only
	 * Cygwin on Win32 require translation for Cygwin based paths.
	 *
	 * @param dir
	 *            directory relative to which the path name is.
	 * @param name
	 *            path name to translate.
	 * @return the translated path. <code>new File(dir,name)</code> if this
	 *         platform does not require path name translation.
	 */
	protected File resolveImpl(final File dir, final String name) {
