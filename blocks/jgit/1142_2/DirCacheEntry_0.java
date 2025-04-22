	public final void smudgeRacilyClean() {
		final int base = infoOffset + P_SIZE;
		Arrays.fill(info
	}

	public final boolean isSmudged() {
		final int base = infoOffset + P_OBJECTID;
		return (getLength() == 0) && (Constants.EMPTY_BLOB_ID.compareTo(info
