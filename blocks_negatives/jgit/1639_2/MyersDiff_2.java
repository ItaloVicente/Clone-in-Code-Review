		Subsequence.toBase(edits, as, bs);
	}

	/**
	 * @return the list of edits found during the last call to {@link #calculateEdits()}
	 */
	public EditList getEdits() {
		return edits;
