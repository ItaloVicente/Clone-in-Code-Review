	/**
	 *
	 * @param repo
	 * @param revTree
	 *            base commit
	 * @param path
	 *            to resource variant
	 * @return new tree walk positioned on given object or <code>null</code>
	 *         when given path was not found in repository
	 * @throws IOException
	 *             when something goes wrong during tree walk initialization
	 */
	protected abstract TreeWalk getTreeWalk(Repository repo, RevTree revTree,
			String path) throws IOException;

