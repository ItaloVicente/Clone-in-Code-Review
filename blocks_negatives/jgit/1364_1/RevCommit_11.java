	/**
	 * Parse this commit buffer for display.
	 *
	 * @param walk
	 *            revision walker owning this reference.
	 * @return parsed commit.
	 */
	public final Commit asCommit(final RevWalk walk) {
		return new Commit(walk.repository, this, buffer);
	}

