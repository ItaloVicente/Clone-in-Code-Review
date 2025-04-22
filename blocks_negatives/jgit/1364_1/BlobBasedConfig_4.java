	public BlobBasedConfig(Config base, final Commit commit, final String path)
			throws FileNotFoundException, IOException, ConfigInvalidException {
		super(base);
		final ObjectId treeId = commit.getTreeId();
		final Repository r = commit.getRepository();
		final TreeWalk tree = TreeWalk.forPath(r, path, treeId);
		if (tree == null)
			throw new FileNotFoundException(MessageFormat.format(JGitText.get().entryNotFoundByPath, path));
		final ObjectId blobId = tree.getObjectId(0);
		ObjectLoader loader = r.open(blobId,Constants.OBJ_BLOB);
		fromText(RawParseUtils.decode(loader.getCachedBytes()));
