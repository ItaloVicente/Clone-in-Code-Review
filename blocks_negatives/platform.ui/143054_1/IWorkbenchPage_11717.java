	 * editor id, as specified by the given match flags. Returns an empty array
	 * if there are no matching editors, or if matchFlags is MATCH_NONE.
	 *
	 * @param input
	 *            the editor input, or <code>null</code> if MATCH_INPUT is not
	 *            specified in matchFlags
	 * @param editorId
	 *            the editor id, or <code>null</code> if MATCH_ID is not
	 *            specified in matchFlags
	 * @param matchFlags
	 *            a bit mask consisting of zero or more of the MATCH_* constants
	 *            OR-ed together
