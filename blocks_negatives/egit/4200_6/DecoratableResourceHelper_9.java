	private static TreeWalk createThreeWayTreeWalk(
			final RepositoryMapping mapping,
			final ArrayList<String> resourcePaths) throws IOException {
		final Repository repository = mapping.getRepository();
		final TreeWalk treeWalk = new TreeWalk(repository);

		final ArrayList<String> paths = new ArrayList<String>(resourcePaths);
		while (paths.remove(null)) {
		}

		treeWalk.setFilter(PathFilterGroup.createFromStrings(paths));
		treeWalk.setRecursive(true);
		treeWalk.reset();

		final ObjectId headId = repository.resolve(Constants.HEAD);
		if (headId != null)
			treeWalk.addTree(new RevWalk(repository).parseTree(headId));
		else
			treeWalk.addTree(new EmptyTreeIterator());

		treeWalk.addTree(new DirCacheIterator(getDirCache(repository)));

		treeWalk.addTree(IteratorService.createInitialIterator(repository));

		return treeWalk;
	}

