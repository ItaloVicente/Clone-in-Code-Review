				}
			}
		}
		return false;
	}

	public static boolean canBeAutoIgnored(IPath path) throws IOException {
		RepositoryMapping mapping = RepositoryMapping.getMapping(path);
		if (mapping == null) {
			return false; // Linked resources may not be mapped
		}
		Repository repository = mapping.getRepository();
		WorkingTreeIterator treeIterator = IteratorService
				.createInitialIterator(repository);
		if (treeIterator == null) {
			return false;
		}
		String repoRelativePath = mapping.getRepoRelativePath(path);
		if (repoRelativePath == null || repoRelativePath.isEmpty()) {
			return false;
		}
		try (TreeWalk walk = new TreeWalk(repository)) {
			walk.addTree(treeIterator);
			walk.setFilter(PathFilter.create(repoRelativePath));
			while (walk.next()) {
				WorkingTreeIterator workingTreeIterator = walk.getTree(0,
						WorkingTreeIterator.class);
				if (walk.getPathString().equals(repoRelativePath)) {
					return !workingTreeIterator.isEntryIgnored();
				}
				if (workingTreeIterator.getEntryFileMode()
						.equals(FileMode.TREE)) {
					walk.enterSubtree();
				}
