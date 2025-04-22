	public SubmoduleWalk setModulesConfig(final Config config) {
		modulesConfig = config;
		return this;
	}

	public SubmoduleWalk readModulesConfig(final AbstractTreeIterator tree)
			throws IOException
		TreeWalk configWalk = new TreeWalk(repository);
		try {
			configWalk.addTree(tree);
			configWalk.setRecursive(false);
			PathFilter filter = PathFilter.create(Constants.DOT_GIT_MODULES);
			configWalk.setFilter(filter);
			while (configWalk.next()) {
				if (filter.isDone(configWalk)) {
					modulesConfig = new BlobBasedConfig(null
							configWalk.getObjectId(0));
					return this;
				}
			}
			throw new ConfigInvalidException(JGitText.get().gitmodulesNotFound);
		} finally {
			configWalk.release();
