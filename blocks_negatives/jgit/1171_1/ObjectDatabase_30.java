	 * Close any resources held by this database and its active alternates.
	 */
	public final void close() {
		closeSelf();
		closeAlternates();
	}

	/**
	 * Close any resources held by this database only; ignoring alternates.
