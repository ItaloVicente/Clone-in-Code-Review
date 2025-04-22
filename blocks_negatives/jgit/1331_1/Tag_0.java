	/**
	 * @return tagger of a annotated tag or null
	 */
	public PersonIdent getAuthor() {
		decode();
		return tagger;
	}

	/**
	 * Set author of an annotated tag.
	 * @param a author identifier as a {@link PersonIdent}
	 */
	public void setAuthor(final PersonIdent a) {
		tagger = a;
	}

