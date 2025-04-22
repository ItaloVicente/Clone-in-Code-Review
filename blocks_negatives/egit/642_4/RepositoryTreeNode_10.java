	/**
	 * We keep this cached in the repository node to avoid repeated lookups
	 *
	 * @return the full branch
	 * @throws IOException
	 */
	public String getBranch() throws IOException {
		if (myType != RepositoryTreeNodeType.REPO) {
			return getRepositoryNode().getBranch();
		}
		if (branch == null) {
			branch = getRepository().getBranch();
		}
		return branch;
	}

