	/**
	 * Get a ref by name.
	 *
	 * @param name
	 *            the name of the ref to lookup. May be a short-hand form, e.g.
	 *            "master" which is is automatically expanded to
	 *            "refs/heads/master" if "refs/heads/master" already exists.
	 * @return the Ref with the given name, or {@code null} if it does not exist
	 * @throws java.io.IOException
	 * @deprecated Use {@link #exactRef(String)} or {@link #findRef(String)}
	 * instead.
	 */
	@Deprecated
	@Nullable
	public Ref getRef(final String name) throws IOException {
		return findRef(name);
	}

