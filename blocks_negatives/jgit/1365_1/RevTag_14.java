	/**
	 * Parse this tag buffer for display.
	 *
	 * @param walk
	 *            revision walker owning this reference.
	 * @return parsed tag.
	 */
	public Tag asTag(final RevWalk walk) {
		return new Tag(walk.repository, this, tagName, buffer);
	}

