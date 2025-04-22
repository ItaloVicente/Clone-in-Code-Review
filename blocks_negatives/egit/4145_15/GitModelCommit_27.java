	/**
	 * Constructor used by JUnits
	 *
	 * @param parent
	 * @param commit
	 * @param direction
	 * @throws IOException
	 */
	GitModelCommit(GitModelRepository parent, RevCommit commit,
			int direction) throws IOException {
		this(parent, commit, direction, null);
