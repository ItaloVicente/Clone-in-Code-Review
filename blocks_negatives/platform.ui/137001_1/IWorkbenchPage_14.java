	 * @param inputs
	 *            the editor inputs
	 * @param editorIDs
	 *            the IDs of the editor extensions to use, in the order of
	 *            inputs
	 * @param mementos
	 *            the mementos representing the state to open the editor with.
	 *            If the supplied memento contains the input's state as well as
	 *            the editor's state then the corresponding entries in the
	 *            'inputs' and 'ids' arrays may be <code>null</code> (they will
	 *            be created from the supplied memento).
	 *
	 * @param matchFlags
	 *            a bit mask consisting of zero or more of the MATCH_* constants
	 *            OR-ed together
	 * @param activateIndex
	 *            the index of the editor to make active or -1 if no activation
	 *            is desired.
