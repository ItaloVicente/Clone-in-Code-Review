
	public static boolean isIgnored(IPath path) throws IOException {
		RepositoryMapping mapping = RepositoryMapping.getMapping(path);
		if (mapping == null)
			return true; // Linked resources may not be mapped
		Repository repository = mapping.getRepository();
		String repoRelativePath = mapping.getRepoRelativePath(path);
		TreeWalk walk = new TreeWalk(repository);
		try {
			walk.addTree(new FileTreeIterator(repository));
			walk.setFilter(PathFilter.create(repoRelativePath));
			while (walk.next()) {
				WorkingTreeIterator workingTreeIterator = walk.getTree(0,
						WorkingTreeIterator.class);
				if (walk.getPathString().equals(repoRelativePath))
					return workingTreeIterator.isEntryIgnored();
				if (workingTreeIterator.getEntryFileMode()
						.equals(FileMode.TREE))
					walk.enterSubtree();
			}
		} finally {
			walk.release();
		}
		return false;
	}
