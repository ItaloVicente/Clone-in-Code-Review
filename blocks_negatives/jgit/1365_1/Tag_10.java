	public String toString() {
		return "tag[" + getTag() + getType() + getObjId() + " " + getTagger() + "]";
	}

	/**
	 * @return SHA-1 of this tag (if annotated and stored).
	 */
	public ObjectId getTagId() {
		return tagId;
