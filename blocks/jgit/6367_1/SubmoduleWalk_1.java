	public SubmoduleWalk setModulesConfig(final Config config) {
		modulesConfig = config;
		return this;
	}

	public SubmoduleWalk readModulesConfig(final AbstractTreeIterator tree)
			throws IOException
		TreeWalk walk = new TreeWalk(repository);
		try {
			walk.addTree(tree);
			walk.setRecursive(false);
			PathFilter filter = PathFilter.create(Constants.DOT_GIT_MODULES);
			walk.setFilter(filter);
			boolean found = false;
			while (walk.next()) {
				if (filter.isDone(walk)) {
					modulesConfig = new BlobBasedConfig(null
							walk.getObjectId(0));
					found = true;
				}
			}
			if (!found) {
				throw new ConfigInvalidException(JGitText.get().gitmodulesNotFound);
			}
		} finally {
			walk.release();
		}
		return this;
	}

	public SubmoduleWalk loadModulesConfig() throws IOException
		File modulesFile = new File(repository.getWorkTree()
				Constants.DOT_GIT_MODULES);
		FileBasedConfig config = new FileBasedConfig(modulesFile
				repository.getFS());
		config.load();
		modulesConfig = config;
		return this;
	}

	private void lazyLoadModulesConfig() throws IOException
