	 * @param tagger
	 */
	public void setTagger(PersonIdent tagger) {
		this.tagger = tagger;
	}

	/**
	 * @return tag target type
	 */
	public String getType() {
		decode();
		return type;
	}

	/**
	 * Set tag target type
	 * @param type
