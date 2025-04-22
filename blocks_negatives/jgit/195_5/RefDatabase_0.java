	/**
	 * Create a symbolic reference from one name to another.
	 *
	 * @param name
	 *            the name of the reference. Should be {@link Constants#HEAD} or
	 *            starting with {@link Constants#R_REFS}.
	 * @param target
	 *            the target of the reference.
	 * @throws IOException
	 *             the reference could not be created or overwritten.
	 */
	public abstract void link(String name, String target) throws IOException;

