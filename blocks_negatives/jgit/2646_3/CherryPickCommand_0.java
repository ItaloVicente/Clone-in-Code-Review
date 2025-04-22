
	/**
	 * @return the list of successfully cherry-picked {@link Ref}'s. Never
	 *         <code>null</code> but maybe an empty list if no commit was
	 *         successfully cherry-picked
	 */
	public List<Ref> getCherryPickedRefs() {
		return cherryPickedRefs;
	}
