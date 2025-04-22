	public SubmoduleWalk setModulesConfig(final Config config) {
		modulesConfig = config;
		return this;
	}

	public SubmoduleWalk setRootTree(final AbstractTreeIterator tree) {
		rootTree = tree;
		modulesConfig = null;
		return this;
	}

	public SubmoduleWalk setRootTree(final AnyObjectId id) throws IOException {
		final CanonicalTreeParser p = new CanonicalTreeParser();
		p.reset(walk.getObjectReader()
		rootTree = p;
		modulesConfig = null;
		return this;
	}

	public SubmoduleWalk loadModulesConfig() throws IOException
		if (rootTree != null) {
			TreeWalk configWalk = new TreeWalk(repository);
			try {
				configWalk.addTree(rootTree);

				int idx;
				for (idx = 0; !rootTree.first(); idx++) {
					rootTree.back(1);
				}

				try {
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
					if (!useWorkingTree) {
						modulesConfig = new Config();
						return this;
					}
				} finally {
					if (idx > 0)
						rootTree.next(idx);
				}
			} finally {
				configWalk.release();
			}
		}
		if (repository.isBare()) {
			modulesConfig = new Config();
		} else {
