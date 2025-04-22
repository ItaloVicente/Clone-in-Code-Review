	/** @return the tips that created this pack, if known. */
	public Set<ObjectId> getTips() {
		return tips;
	}

	/**
	 * @param tips
	 *            the tips of the pack, null if it has no known tips.
	 * @return {@code this}
	 */
	public DfsPackDescription setTips(Set<ObjectId> tips) {
		this.tips = tips;
		return this;
	}

