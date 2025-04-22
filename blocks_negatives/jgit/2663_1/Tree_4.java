	public void accept(final TreeVisitor tv, final int flags)
			throws IOException {
		final TreeEntry[] c;

		if ((MODIFIED_ONLY & flags) == MODIFIED_ONLY && !isModified())
			return;

		if ((LOADED_ONLY & flags) == LOADED_ONLY && !isLoaded()) {
			tv.startVisitTree(this);
			tv.endVisitTree(this);
			return;
		}

		ensureLoaded();
		tv.startVisitTree(this);

		if ((CONCURRENT_MODIFICATION & flags) == CONCURRENT_MODIFICATION)
			c = members();
		else
			c = contents;

		for (int k = 0; k < c.length; k++)
			c[k].accept(tv, flags);

		tv.endVisitTree(this);
	}

