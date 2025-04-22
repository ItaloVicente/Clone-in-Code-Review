
	}

	/**
	 * Constructor used by JUnits
	 *
	 * @param parent
	 * @param baseCommit
	 * @throws IOException
	 */
	GitModelCache(GitModelObject parent, RevCommit baseCommit)
			throws IOException {
		this(parent, baseCommit, null);
