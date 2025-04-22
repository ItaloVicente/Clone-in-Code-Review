	public final void close() {
		closeSelf();
		closeAlternates();
	}

	/**
	 * Close any resources held by this database only; ignoring alternates.
	 * <p>
	 * To fully close this database and its referenced alternates, the caller
	 * should instead invoke {@link #close()}.
	 */
	public void closeSelf() {
	}

	/** Fully close all loaded alternates and clear the alternate list. */
	public final void closeAlternates() {
		ObjectDatabase[] alt = alternates.get();
		if (alt != null) {
			alternates.set(null);
			closeAlternates(alt);
		}
	}
