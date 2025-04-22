	public BlobBasedConfig(Config base
			String path) throws FileNotFoundException
			ConfigInvalidException {
		this(base
	}

	private static byte[] read(Repository db
			throws MissingObjectException
			IOException {
		ObjectReader or = db.newObjectReader();
		try {
			TreeWalk tree = TreeWalk.forPath(or
			if (tree == null)
				throw new FileNotFoundException(MessageFormat.format(JGitText
						.get().entryNotFoundByPath
			return read(or
		} finally {
			or.release();
		}
	}

	private static AnyObjectId asTree(ObjectReader or
			throws MissingObjectException
			IOException {
		if (treeish instanceof RevTree)
			return treeish;

		if (treeish instanceof RevCommit
				&& ((RevCommit) treeish).getTree() != null)
			return ((RevCommit) treeish).getTree();

		return new RevWalk(or).parseTree(treeish).getId();
