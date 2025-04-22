	/**
	 * See @{link {@link #accept(TreeVisitor, int)}.
	 *
	 * @param tv
	 * @throws IOException
	 */
	public void accept(final TreeVisitor tv) throws IOException {
		accept(tv, 0);
	}

	/**
	 * Visit the members of this TreeEntry.
	 *
	 * @param tv
	 *            A visitor object doing the work
	 * @param flags
	 *            Specification for what members to visit. See
	 *            {@link #MODIFIED_ONLY}, {@link #LOADED_ONLY},
	 *            {@link #CONCURRENT_MODIFICATION}.
	 * @throws IOException
	 */
	public abstract void accept(TreeVisitor tv, int flags) throws IOException;

