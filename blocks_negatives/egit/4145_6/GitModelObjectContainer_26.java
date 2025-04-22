	protected int kind = -1;

	private String name;

	private GitModelObject[] children;

	/**
	 * Base commit connected with this container
	 */
	protected final RevCommit baseCommit;

	/**
	 * Remote commit connected with this container
	 */
	protected final RevCommit remoteCommit;

	/**
	 *
	 * @param parent instance of parent object
	 * @param commit commit connected with this container
	 * @param direction indicate change direction
	 * @throws IOException
	 */
	protected GitModelObjectContainer(GitModelObject parent, RevCommit commit,
			int direction) throws IOException {
