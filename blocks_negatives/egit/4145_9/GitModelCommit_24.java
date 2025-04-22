	/**
	 * Constructor for child classes.
	 *
	 * @param parent
	 *            instance of repository model object that is parent for this
	 *            commit
	 * @param commit
	 *            instance of commit that will be associated with this model
	 *            object
	 * @param ancestorCommit
	 *            common ancestor commit for object that is wrapped
	 * @param direction
	 *            use {@link Differencer#LEFT} and {@link Differencer#RIGHT} to
	 *            determinate commit direction (is it incoming or outgoing)
	 * @throws IOException
	 */
	protected GitModelCommit(GitModelObject parent, RevCommit commit,
			RevCommit ancestorCommit, int direction) throws IOException {
		super(parent, commit, direction);
